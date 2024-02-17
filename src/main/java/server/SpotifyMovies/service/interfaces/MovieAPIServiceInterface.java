package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.dto.MovieShortDTO;

import java.util.List;

public interface MovieAPIServiceInterface {
    List<Movie> getAllListMovies();

    List<MovieShortDTO> searchMultiByName(String name);

    List<MovieShortDTO> getPopularMovies(int noPage);

    List<MovieShortDTO> getTrendingAll();

    List<MovieShortDTO> getTrendingMovies();

    List<MovieShortDTO> getTrendingTvShows();

    List<MovieShortDTO> upcoming(int noPage);

    List<MovieShortDTO> topRatedMovies(int noPage);

    List<MovieShortDTO> topRatedTvShows(int noPage);
}
