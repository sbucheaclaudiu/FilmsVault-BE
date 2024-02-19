package server.SpotifyMovies.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.CreatePlaylistDTO;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.repository.PlaylistRepoInterface;
import server.SpotifyMovies.service.interfaces.PlaylistServiceInterface;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaylistService implements PlaylistServiceInterface {
    @Autowired
    private PlaylistRepoInterface playlistRepo;

    @Override
    public List<Playlist> getPlaylistByUser(Long userId) {
        return playlistRepo.getPlaylistByUserId(userId);
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
