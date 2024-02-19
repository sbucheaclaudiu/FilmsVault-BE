package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.id.PlaylistMovieId;

@Repository
public interface MovieRepo extends JpaRepository<Movie, PlaylistMovieId> {

}
