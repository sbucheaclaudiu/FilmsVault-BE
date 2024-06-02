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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JsonNodeToDTO implements JsonNodeToDTOInterface{
    @Override
    public MovieShortDTO createMovieShort(JsonNode resultNode) {
        String id = resultNode.get("id").asText();
        boolean person = false;
        boolean movie = false;

        String title = "";
        try {
            title = resultNode.get("title").asText();
            movie = true;
        } catch (Exception e) {
            title = resultNode.get("name").asText();
        }

        String posterPath = "";
        try {
            posterPath = "https://image.tmdb.org/t/p/original" + resultNode.get("poster_path").asText();
        } catch (Exception e) {
            posterPath = "https://image.tmdb.org/t/p/original" + resultNode.get("profile_path").asText();
            person = true;
        }


        String type = "";
        if(person){
            type = "person";
        }
        else if(movie){
            type = "movie";
        }
        else{
            type = "tv";
        }

        double rating = 0;
        String genres = "";
        String releaseDate = "";

        if(!type.equals("person")){
            rating = resultNode.get("vote_average").asDouble();

            JsonNode genresNode = resultNode.get("genres");

            if (genresNode != null && genresNode.isArray()) {
                StringBuilder genresStringBuilder = new StringBuilder();

                for (JsonNode genreNode : genresNode) {
                    String genreName = genreNode.get("name").asText();
                    genresStringBuilder.append(genreName).append(", ");
                }

                genres = genresStringBuilder.toString().replaceAll(", $", "");

            }

            if(type == "movie"){
                releaseDate = resultNode.get("release_date").asText();
            }
            else{
                releaseDate = resultNode.get("first_air_date").asText();
            }
        }

        return new MovieShortDTO(id, title, posterPath, type, genres, releaseDate, rating);
    }

    @Override
    public MovieDetailsDTO createMovieDetails(JsonNode jsonNode){
        String id = jsonNode.get("id").asText();
        String type = "movie";
        String posterPath =  "https://image.tmdb.org/t/p/original" + jsonNode.get("poster_path").asText();
        String backdropPath = "https://image.tmdb.org/t/p/original" + jsonNode.get("backdrop_path").asText();
        String name = jsonNode.get("title").asText();
        String overview = jsonNode.get("overview").asText();
        String releaseDate = jsonNode.get("release_date").asText();
        String runtime = jsonNode.get("runtime").asText();
        double rating = jsonNode.get("vote_average").asDouble();
        long budget = jsonNode.get("budget").asLong();
        long revenue = jsonNode.get("revenue").asLong();
        String genres = "";

        JsonNode genresNode = jsonNode.get("genres");

        if (genresNode != null && genresNode.isArray()) {
            StringBuilder genresStringBuilder = new StringBuilder();

            for (JsonNode genreNode : genresNode) {
                String genreName = genreNode.get("name").asText();
                genresStringBuilder.append(genreName).append(", ");
            }

            genres = genresStringBuilder.toString().replaceAll(", $", "");

        }

        return new MovieDetailsDTO(id, type, posterPath, backdropPath, name, overview, releaseDate, runtime, Math.floor(rating * 10) / 10, budget, revenue, genres);
    }

    @Override
    public TVDetailsDTO createTVDetails(JsonNode jsonNode){
        String id = jsonNode.get("id").asText();
        String type = "tv";
        String posterPath =  "https://image.tmdb.org/t/p/original" + jsonNode.get("poster_path").asText();
        String backdropPath = "https://image.tmdb.org/t/p/original" + jsonNode.get("backdrop_path").asText();
        String name = jsonNode.get("name").asText();
        String overview = jsonNode.get("overview").asText();
        String releaseDate = jsonNode.get("first_air_date").asText();
        String lastDate = jsonNode.get("last_air_date").asText();
        String noOfSeasons = jsonNode.get("number_of_seasons").asText();
        String noOfEpisods = jsonNode.get("number_of_episodes").asText();
        double rating = jsonNode.get("vote_average").asDouble();
        String genres = "";

        JsonNode genresNode = jsonNode.get("genres");

        if (genresNode != null && genresNode.isArray()) {
            StringBuilder genresStringBuilder = new StringBuilder();

            for (JsonNode genreNode : genresNode) {
                String genreName = genreNode.get("name").asText();
                genresStringBuilder.append(genreName).append(", ");
            }

            genres = genresStringBuilder.toString().replaceAll(", $", "");

        }

        return new TVDetailsDTO(id, type, backdropPath, posterPath, name, overview, releaseDate, lastDate, noOfSeasons, noOfEpisods, Math.floor(rating * 10) / 10, genres);
    }

    private String transformDateToString(String birthday) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormat.parse(birthday);

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = outputFormat.format(date);

        return formattedDate;
    }

    @Override
    public PersonDetailsDTO createPersonDetails(JsonNode jsonNode) throws ParseException {
        String id = jsonNode.get("id").asText();
        String type = "person";
        String name = jsonNode.get("name").asText();
        String profilePath =  "https://image.tmdb.org/t/p/original" + jsonNode.get("profile_path").asText();
        String biography = jsonNode.get("biography").asText();
        String birthday = transformDateToString(jsonNode.get("birthday").asText());
        String placeOfBirth = jsonNode.get("place_of_birth").asText();

        return new PersonDetailsDTO(id, type, name, profilePath, biography, birthday, placeOfBirth);
    }

    @Override
    public PersonCastDTO createPersonCast(JsonNode resultNode){
        String id = resultNode.get("id").asText();
        String profilePath = "https://image.tmdb.org/t/p/original" + resultNode.get("profile_path").asText();

        String name = resultNode.get("name").asText();
        String character = resultNode.get("character").asText();

        return new PersonCastDTO(id, name, profilePath, character);
    }

    @Override
    public PersonShortDTO createPersonShort(JsonNode resultNode){
        String id = resultNode.get("id").asText();
        String profilePath = "https://image.tmdb.org/t/p/original" + resultNode.get("profile_path").asText();

        String name = resultNode.get("name").asText();

        return new PersonShortDTO(id, name, profilePath);
    }

    @Override
    public VideoDTO createVideo(JsonNode resultNode){
        String id = resultNode.get("id").asText();

        String videoPath = resultNode.get("key").asText();

        return new VideoDTO(id, videoPath);
    }

    @Override
    public List<String> createListRecomendtions(String resultNode) {
        return null;
    }
}
