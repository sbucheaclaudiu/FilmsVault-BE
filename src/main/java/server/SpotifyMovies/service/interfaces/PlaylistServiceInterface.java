package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.playlist.CreatePlaylistDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.dto.playlist.UpdatePlaylistDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface PlaylistServiceInterface {
    List<PlaylistDTO> getPlaylistsByUser(Long userId) throws ParseException, IOException;

    List<PlaylistDTO> getPlaylistsByUserPublic(Long userId) throws ParseException, IOException;

    List<PlaylistDTO> getRandomPlaylists(Long userId) throws ParseException, IOException;

    List<PlaylistDTO> getPlaylistByName(String name) throws ParseException, IOException;

    PlaylistDTO getPlaylistById(Long id) throws CustomException, ParseException, IOException;

    void createDefaultPlaylists(User user);

    void createPlaylist(CreatePlaylistDTO playlistDTO, User user) throws Exception;

    void updatePlaylist(UpdatePlaylistDTO updatePlaylistDTO, User user) throws Exception;

    void deletePlaylist(Long playlistId);

}
