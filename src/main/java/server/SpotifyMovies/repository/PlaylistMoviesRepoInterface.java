package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.PlaylistMovie;
import server.SpotifyMovies.model.id.PlaylistMovieId;

import java.util.List;

@Repository
public interface PlaylistMoviesRepoInterface extends JpaRepository<PlaylistMovie, PlaylistMovieId> {
    PlaylistMovie getPlaylistMovieById(PlaylistMovieId id);

    void deleteById(PlaylistMovieId id);

    List<PlaylistMovie> findByPlaylistId(Long playlistId);
}
