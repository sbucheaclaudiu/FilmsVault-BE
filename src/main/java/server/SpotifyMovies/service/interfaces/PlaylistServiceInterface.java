package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.CreatePlaylistDTO;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.User;

import java.util.List;

public interface PlaylistServiceInterface {
    List<Playlist> getPlaylistByUser(Long userId);

    void createDefaultPlaylists(User user);

    void createPlaylist(CreatePlaylistDTO playlistDTO, User user);
}
