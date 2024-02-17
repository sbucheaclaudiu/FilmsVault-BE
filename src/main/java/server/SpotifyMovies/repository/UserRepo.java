package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.SpotifyMovies.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
