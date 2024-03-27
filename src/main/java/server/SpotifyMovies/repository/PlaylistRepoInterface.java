package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.Playlist;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlaylistRepoInterface extends JpaRepository<Playlist, Long> {
    List<Playlist> getPlaylistByUserId(Long userId);

    List<Playlist> getPlaylistByUserIdAndPrivatePlaylist(Long userId, boolean privatePlaylist);

    Playlist findByUserIdAndName(Long userId, String name);

    Playlist getPlaylistsById(Long id);

}
