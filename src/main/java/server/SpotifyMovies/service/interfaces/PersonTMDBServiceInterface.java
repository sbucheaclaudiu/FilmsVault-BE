package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.movie.MovieShortDTO;
import server.SpotifyMovies.dto.person.PersonCastDTO;
import server.SpotifyMovies.dto.person.PersonDetailsDTO;
import server.SpotifyMovies.dto.person.PersonShortDTO;

import java.text.ParseException;
import java.util.List;

public interface PersonTMDBServiceInterface {

    PersonDetailsDTO getPersonDetails(String id) throws ParseException;

    List<PersonShortDTO> getPopularPersons(String apiUrl);

    List<PersonCastDTO> getCast(String id, String type);

    List<MovieShortDTO> getMovieCredits(String id);

}
