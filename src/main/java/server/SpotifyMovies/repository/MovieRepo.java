package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.id.ListMovieId;

public interface MovieRepo extends JpaRepository<Movie, ListMovieId> {

}
