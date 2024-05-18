package server.SpotifyMovies.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.follow.FollowedUserDTO;
import server.SpotifyMovies.dto.follow.LastMovieAddDetailsDTO;
import server.SpotifyMovies.dto.follow.PostFollowersDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.mapper.JsonNodeToDTO;
import server.SpotifyMovies.mapper.JsonNodeToDTOInterface;
import server.SpotifyMovies.mapper.ModelToDTO;
import server.SpotifyMovies.mapper.ModelToDTOInterface;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.PlaylistMovie;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.model.id.FollowersId;
import server.SpotifyMovies.repository.FollowersRepoInterface;
import server.SpotifyMovies.repository.PlaylistRepoInterface;
import server.SpotifyMovies.repository.UserRepoInterface;
import server.SpotifyMovies.service.interfaces.FollowersServiceInterface;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class FollowersService implements FollowersServiceInterface {
    @Autowired
    private FollowersRepoInterface followersRepo;

    @Autowired
    private UserRepoInterface userRepo;

    @Autowired
    private PlaylistRepoInterface playlistRepo;

    private final ModelToDTOInterface modelToDTOMapper = new ModelToDTO();

    private LastMovieAddDetailsDTO getLastMovieWatchedFromUser(Long userId){
        Playlist watchedPlaylist = playlistRepo.findByUserIdAndName(userId, "Watched");

        List<PlaylistMovie> playlistMovies = watchedPlaylist.getPlaylistMovies();
        playlistMovies.sort(Comparator.comparing(PlaylistMovie::getDateAddedToList).reversed());

        if (!playlistMovies.isEmpty()) {
            return new LastMovieAddDetailsDTO(playlistMovies.get(0).getMovie().getMovieName(), playlistMovies.get(0).getMovie().getTmdbId(), playlistMovies.get(0).getMovie().getType());
        }

        return new LastMovieAddDetailsDTO("", 0L, "");
    }

    private LastMovieAddDetailsDTO getLastMovieWatchlistFromUser(Long userId){
        Playlist watchedPlaylist = playlistRepo.findByUserIdAndName(userId, "Watchlist");

        List<PlaylistMovie> playlistMovies = watchedPlaylist.getPlaylistMovies();
        playlistMovies.sort(Comparator.comparing(PlaylistMovie::getDateAddedToList).reversed());

        if (!playlistMovies.isEmpty()) {
            return new LastMovieAddDetailsDTO(playlistMovies.get(0).getMovie().getMovieName(), playlistMovies.get(0).getMovie().getTmdbId(), playlistMovies.get(0).getMovie().getType());
        }

        return  new LastMovieAddDetailsDTO("", 0L, "");
    }
    @Override
    public List<FollowedUserDTO> getFollowersByUser(Long userId) throws IOException {
        List<FollowedUserDTO> lst = modelToDTOMapper.followedListToDTOList(followersRepo.findAllActiveByIdUserId(userId));

        for(FollowedUserDTO followedUserDTO : lst){
            LastMovieAddDetailsDTO lastMovieWatched = getLastMovieWatchedFromUser(followedUserDTO.getId());
            followedUserDTO.setLastWatchedName(lastMovieWatched.getName());
            followedUserDTO.setLastWatchedTmdbId(lastMovieWatched.getTmdbId());
            followedUserDTO.setLastWatchedType(lastMovieWatched.getType());

            LastMovieAddDetailsDTO lastMovieWatchlist = getLastMovieWatchlistFromUser(followedUserDTO.getId());
            followedUserDTO.setLastWatchlistName(lastMovieWatchlist.getName());
            followedUserDTO.setLastWatchlistTmdbId(lastMovieWatchlist.getTmdbId());
            followedUserDTO.setLastWatchlistType(lastMovieWatchlist.getType());
        }

        return lst;
    }

    @Override
    public Followers addFollowUser(PostFollowersDTO followUserDTO) throws CustomException {
        User user = userRepo.findById(followUserDTO.getUserId()).orElse(null);
        User followedUser = userRepo.findById(followUserDTO.getFollowedUserId()).orElse(null);

        if(user != null && followedUser != null){
            if (!followersRepo.existsById_UserAndId_FollowedUser(user, followedUser)) {
                FollowersId followersId = new FollowersId(user, followedUser);
                Followers followers = new Followers(followersId);
                followersRepo.save(followers);
            } else {
                throw new CustomException("You allready follow this user.");
            }
        }
        else{
            throw new CustomException("User not found.");
        }
        return null;
    }

    @Override
    public Followers deleteFollowUser(PostFollowersDTO followUserDTO) throws CustomException {
        User user = userRepo.findById(followUserDTO.getUserId()).orElse(null);
        User followedUser = userRepo.findById(followUserDTO.getFollowedUserId()).orElse(null);

        if(user != null && followedUser != null){
            if (followersRepo.existsById_UserAndId_FollowedUser(user, followedUser)) {
                FollowersId followersId = new FollowersId(user, followedUser);
                followersRepo.deleteById(followersId);
            } else {
                throw new CustomException("You don't follow this user.");
            }
        }
        else{
            throw new CustomException("User not found.");
        }
        return null;
    }
}
