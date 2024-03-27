package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.playlist.CreatePlaylistDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.model.User;

import java.text.ParseException;
import java.util.List;

public interface PlaylistServiceInterface {
    List<PlaylistDTO> getPlaylistsByUser(Long userId) throws ParseException;

    List<PlaylistDTO> getPlaylistsByUserPublic(Long userId) throws ParseException;

    PlaylistDTO getPlaylistById(Long id) throws CustomException, ParseException;

    void createDefaultPlaylists(User user);

    void createPlaylist(CreatePlaylistDTO playlistDTO, User user);
}
