package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.Playlist;
import java.util.List;

@Repository
public interface PlaylistRepoInterface extends JpaRepository<Playlist, Long> {
    List<Playlist> getPlaylistByUserId(Long userId);

}