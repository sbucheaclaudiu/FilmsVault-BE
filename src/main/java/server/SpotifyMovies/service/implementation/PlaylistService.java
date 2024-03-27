package server.SpotifyMovies.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.playlist.CreatePlaylistDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.mapper.ModelToDTO;
import server.SpotifyMovies.mapper.ModelToDTOInterface;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.repository.PlaylistRepoInterface;
import server.SpotifyMovies.service.interfaces.PlaylistServiceInterface;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService implements PlaylistServiceInterface {
    @Autowired
    private PlaylistRepoInterface playlistRepo;

    private final ModelToDTOInterface modelToDTOMapper = new ModelToDTO();

    @Override
    public List<PlaylistDTO> getPlaylistsByUser(Long userId) throws ParseException {
        return modelToDTOMapper.playlistListToDTOList(playlistRepo.getPlaylistByUserId(userId));
    }

    @Override
    public List<PlaylistDTO> getPlaylistsByUserPublic(Long userId) throws ParseException {
        return modelToDTOMapper.playlistListToDTOList(playlistRepo.getPlaylistByUserIdAndPrivatePlaylist(userId, false));
    }

    @Override
    public PlaylistDTO getPlaylistById(Long id) throws CustomException, ParseException {
        Playlist playlist;
        Optional<Playlist> optionalPlaylist = playlistRepo.findById(id);
        if (optionalPlaylist.isPresent()) {
            playlist = optionalPlaylist.get();

        } else {
            throw new CustomException("Playlist not found");
        }

        return modelToDTOMapper.playlistToDTO(playlist);
    }

    @Override
    public void createDefaultPlaylists(User user) {
        Playlist watched = new Playlist("Watched", "Watched Films: My cinematic journey in a nutshell.", LocalDateTime.now(), LocalDateTime.now(), false, user, "watched");
        Playlist watchlist = new Playlist("Watchlist", "Film Wishlist: A compilation of movies I aspire to watch, my cinematic to-do list.", LocalDateTime.now(), LocalDateTime.now(), false, user, "watchlist");

        playlistRepo.save(watched);
        playlistRepo.save(watchlist);
    }

    @Override
    public void createPlaylist(CreatePlaylistDTO playlistDTO, User user) {

        Playlist playlist = new Playlist(playlistDTO.getName(), playlistDTO.getDescription(), LocalDateTime.now(), LocalDateTime.now(), playlistDTO.isPrivatePlaylist(), user, playlistDTO.getImagePath());

        playlistRepo.save(playlist);
    }
}
