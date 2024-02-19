package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.MovieShortDTO;

import java.util.List;

public interface TMDBAPIServiceInterface {

    List<MovieShortDTO> searchMultiByName(String name);

    List<MovieShortDTO> getMovies(String apiUrl);

}
