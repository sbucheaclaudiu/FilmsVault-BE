package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.*;

import java.util.List;

public interface TMDBAPIServiceInterface {

    List<MovieShortDTO> searchMultiByName(String name);

    List<MovieShortDTO> getMovies(String apiUrl);

    MovieDetailsDTO getMovieDetails(String id);

    TVDetailsDTO getTVDetails(String id);

    PersonDetailsDTO getPersonDetails(String id);

    List<PersonShortDTO> getCast(String id, String type);

    List<VideoDTO> getVideo(String id, String type);

    List<MovieShortDTO> getMovieCredits(String id);

}
