package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT p FROM Playlist p WHERE p.user.id != :userId AND p.privatePlaylist = false")
    List<Playlist> findFilteredPlaylistsByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Playlist p WHERE p.name LIKE %:name% AND p.privatePlaylist = false")
    List<Playlist> findPublicPlaylistsByNameContaining(@Param("name") String name);

}
