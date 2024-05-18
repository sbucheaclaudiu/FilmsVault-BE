package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepoInterface extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);

    @Query("SELECT COUNT(p) FROM Playlist p WHERE p.user.id = :userId AND p.privatePlaylist = false")
    int countPublicPlaylistsByUserId(@Param("userId") Long userId);

    List<User> findByUsernameContainingAndActiveAccountIsTrue(String searchString);


}
