package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.id.FollowersId;

public interface FollowersRepoInterface extends JpaRepository<Followers, FollowersId> {
}
