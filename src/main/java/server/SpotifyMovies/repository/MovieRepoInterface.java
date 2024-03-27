package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.id.PlaylistMovieId;

import java.util.List;

@Repository
public interface MovieRepoInterface extends JpaRepository<Movie, Long> {
    Movie findByTmdbId(Long tmdbId);
    boolean existsByTmdbId(Long tmdbId);
}
