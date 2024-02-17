package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.SpotifyMovies.model.ListDetails;

public interface ListDetailsRepoInterface extends JpaRepository<ListDetails, Long> {
}
