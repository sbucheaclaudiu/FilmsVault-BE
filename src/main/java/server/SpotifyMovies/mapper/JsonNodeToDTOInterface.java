package server.SpotifyMovies.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import server.SpotifyMovies.dto.movie.MovieDetailsDTO;
import server.SpotifyMovies.dto.movie.MovieShortDTO;
import server.SpotifyMovies.dto.movie.TVDetailsDTO;
import server.SpotifyMovies.dto.movie.VideoDTO;
import server.SpotifyMovies.dto.person.PersonCastDTO;
import server.SpotifyMovies.dto.person.PersonDetailsDTO;
import server.SpotifyMovies.dto.person.PersonShortDTO;

import java.text.ParseException;
import java.util.List;

public interface JsonNodeToDTOInterface {
    MovieShortDTO createMovieShort(JsonNode resultNode);
    MovieDetailsDTO createMovieDetails(JsonNode jsonNode);
    TVDetailsDTO createTVDetails(JsonNode jsonNode);
    PersonDetailsDTO createPersonDetails(JsonNode jsonNode) throws ParseException;
    PersonCastDTO createPersonCast(JsonNode resultNode);
    PersonShortDTO createPersonShort(JsonNode resultNode);
    VideoDTO createVideo(JsonNode resultNode);
    List<String> createListRecomendtions(String resultNode);
}
