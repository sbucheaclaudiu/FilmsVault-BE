package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.id.FollowersId;

@Repository
public interface FollowersRepoInterface extends JpaRepository<Followers, FollowersId> {
}
