package server.SpotifyMovies.mapper;

import server.SpotifyMovies.dto.follow.FollowedUserDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.Playlist;

import java.text.ParseException;
import java.util.List;

public interface ModelToDTOInterface {
    PlaylistDTO playlistToDTO(Playlist playlist) throws ParseException;

    List<PlaylistDTO> playlistListToDTOList(List<Playlist> lstPlaylist) throws ParseException;

    FollowedUserDTO followedUserToDTO(Followers followers);
    List<FollowedUserDTO> followedListToDTOList(List<Followers> lstFollowers);
}
