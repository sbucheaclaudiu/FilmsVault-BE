package server.SpotifyMovies.mapper;

import server.SpotifyMovies.dto.follow.FollowedUserDTO;
import server.SpotifyMovies.dto.login.UserDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ModelToDTOInterface {
    PlaylistDTO playlistToDTO(Playlist playlist) throws ParseException, IOException;

    List<PlaylistDTO> playlistListToDTOList(List<Playlist> lstPlaylist) throws ParseException, IOException;

    FollowedUserDTO followedUserToDTO(Followers followers);
    List<FollowedUserDTO> followedListToDTOList(List<Followers> lstFollowers);

    UserDTO userToDTO(User user) throws IOException;
}
