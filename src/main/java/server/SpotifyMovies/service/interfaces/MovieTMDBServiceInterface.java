package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.movie.MovieDetailsDTO;
import server.SpotifyMovies.dto.movie.MovieShortDTO;
import server.SpotifyMovies.dto.movie.TVDetailsDTO;
import server.SpotifyMovies.dto.movie.VideoDTO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MovieTMDBServiceInterface {
    List<MovieShortDTO> searchMultiByName(String name);

    List<MovieShortDTO> getMovies(String apiUrl);

    List<MovieShortDTO> getRecommendations(String title) throws IOException;

    MovieDetailsDTO getMovieDetails(String id);

    TVDetailsDTO getTVDetails(String id);

    List<VideoDTO> getVideo(String id, String type);
}
