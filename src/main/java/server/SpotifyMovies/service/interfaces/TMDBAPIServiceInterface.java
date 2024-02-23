package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.MovieDetailsDTO;
import server.SpotifyMovies.dto.MovieShortDTO;
import server.SpotifyMovies.dto.PersonShortDTO;
import server.SpotifyMovies.dto.VideoDTO;

import java.util.List;

public interface TMDBAPIServiceInterface {

    List<MovieShortDTO> searchMultiByName(String name);

    List<MovieShortDTO> getMovies(String apiUrl);

    MovieDetailsDTO getMovieDetails(String id);

    List<PersonShortDTO> getMovieCast(String id);

    List<VideoDTO> getMovieVideo(String id);

}
