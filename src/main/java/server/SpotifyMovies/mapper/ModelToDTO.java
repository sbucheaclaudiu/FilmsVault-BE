package server.SpotifyMovies.mapper;

import server.SpotifyMovies.dto.follow.FollowedUserDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.Playlist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelToDTO implements ModelToDTOInterface{
    private final Utils utils = new Utils();

    @Override
    public PlaylistDTO playlistToDTO(Playlist playlist) throws ParseException {
        return new PlaylistDTO(playlist.getId(), playlist.getName(), playlist.getUser().getName(), playlist.getImagePath(), utils.transformDateToString(playlist.getLastTimeEdit().toString()));
    }

    @Override
    public List<PlaylistDTO> playlistListToDTOList(List<Playlist> lstPlaylist) throws ParseException {
        List<PlaylistDTO> lstPlaylistDTO = new ArrayList<>();

        for(Playlist playlist : lstPlaylist){
            lstPlaylistDTO.add(playlistToDTO(playlist));
        }

        return lstPlaylistDTO;
    }

    @Override
    public FollowedUserDTO followedUserToDTO(Followers followers) {
        return new FollowedUserDTO(followers.getId().getFollowedUser().getId(), followers.getId().getFollowedUser().getUsernameReal(), followers.getId().getFollowedUser().getProfile_url());
    }

    @Override
    public List<FollowedUserDTO> followedListToDTOList(List<Followers> lstFollowers) {
        List<FollowedUserDTO> lstfollowedUserDTO = new ArrayList<>();

        for(Followers followers : lstFollowers){
            lstfollowedUserDTO.add(followedUserToDTO(followers));
        }

        return lstfollowedUserDTO;
    }



}
