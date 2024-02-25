package server.SpotifyMovies.service.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.*;
import server.SpotifyMovies.service.interfaces.TMDBAPIServiceInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class TMDBAPIService implements TMDBAPIServiceInterface {
    private final String apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjNhMzgzMDM2YjZjMWFhOWI0YmNmMzY0YWZkOTRkMyIsInN1YiI6IjY1NzM1ZDBiMDA2YjAxMDBlMWE5N2JhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PaamffUcloAVp0SaezXIMBzXUhnXD0Lrv-gNLZ1huYs";

    private ObjectMapper objectMapper = new ObjectMapper();

    private Random random = new Random();


    private HttpResponse<String> apiCall(String apiUrl) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .setHeader("accept", "application/json")
                    .setHeader("Authorization", apiKey)
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private MovieShortDTO getMovieShortFromJsonNode(JsonNode resultNode) {
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

        return new MovieShortDTO(id, title, posterPath, type);
    }

    @Override
    public List<MovieShortDTO> searchMultiByName(String name) {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/search/multi?query=" + name + "&include_adult=false&language=en-US&page=1";

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    lstPopularMovies.add(getMovieShortFromJsonNode(resultNode));
                }

                return lstPopularMovies;
            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<MovieShortDTO> getMovies(String apiUrl) {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {
                    lstPopularMovies.add(getMovieShortFromJsonNode(resultNode));
                }

            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return lstPopularMovies;
    }

    private MovieDetailsDTO createMovieDetails(JsonNode jsonNode){
        String id = jsonNode.get("id").asText();
        String type = "movie";
        String posterPath =  "https://image.tmdb.org/t/p/original" + jsonNode.get("poster_path").asText();
        String backdropPath = "https://image.tmdb.org/t/p/original" + jsonNode.get("backdrop_path").asText();
        String name = jsonNode.get("original_title").asText();
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

            System.out.println("Genres: " + genres);
        } else {
            System.out.println("No genres found in the response.");
        }

        return new MovieDetailsDTO(id, type, posterPath, backdropPath, name, overview, releaseDate, runtime, rating, budget, revenue, genres);
    }
    @Override
    public MovieDetailsDTO getMovieDetails(String id) {
        String apiUrl = "https://api.themoviedb.org/3/movie/" + id + "?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                return createMovieDetails(jsonNode);

            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return null;
    }

    private TVDetailsDTO createTVDetails(JsonNode jsonNode){
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

        return new TVDetailsDTO(id, type, backdropPath, posterPath, name, overview, releaseDate, lastDate, noOfSeasons, noOfEpisods, rating, genres);
    }

    @Override
    public TVDetailsDTO getTVDetails(String id) {
        String apiUrl = "https://api.themoviedb.org/3/tv/" + id + "?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                return createTVDetails(jsonNode);

            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return null;
    }

    private PersonDetailsDTO createPersonDetails(JsonNode jsonNode){
        String id = jsonNode.get("id").asText();
        String type = "person";
        String name = jsonNode.get("name").asText();
        String profilePath =  "https://image.tmdb.org/t/p/original" + jsonNode.get("profile_path").asText();
        String biography = jsonNode.get("biography").asText();
        String birthday = jsonNode.get("birthday").asText();
        String placeOfBirth = jsonNode.get("place_of_birth").asText();

        return new PersonDetailsDTO(id, type, name, profilePath, biography, birthday, placeOfBirth);
    }

    private String getBackdropPerson(String id){
        String apiUrl = "https://api.themoviedb.org/3/person/" + id + "/combined_credits?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = jsonNode.get("cast");

                int noOfResults = resultsNode.size();

                int noPage = random.nextInt(noOfResults + 1);
                JsonNode resultNode = resultsNode.get(noPage);
                return "https://image.tmdb.org/t/p/original" + resultNode.get("backdrop_path").asText();

            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return "";
    }

    @Override
    public PersonDetailsDTO getPersonDetails(String id) {
        String apiUrl = "https://api.themoviedb.org/3/person/" + id + "?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                PersonDetailsDTO person = createPersonDetails(jsonNode);
                person.setBackdropPath(getBackdropPerson(id));
                return person;

            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return null;
    }

    private PersonShortDTO createPersonShort(JsonNode resultNode){
        String id = resultNode.get("id").asText();

        String profilePath = "https://image.tmdb.org/t/p/original" + resultNode.get("profile_path").asText();

        String name = resultNode.get("name").asText();

        String character = resultNode.get("character").asText();

        return new PersonShortDTO(id, name, profilePath, character);
    }

    @Override
    public List<PersonShortDTO> getCast(String id, String type) {
        List<PersonShortDTO> lstPersons = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/" + type + "/" + id + "/credits?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = jsonNode.get("cast");

                for (JsonNode resultNode : resultsNode) {
                    JsonNode profilePathNode = resultNode.get("profile_path");
                    if (profilePathNode != null && !profilePathNode.isNull()) {
                        lstPersons.add(createPersonShort(resultNode));
                    }
                }


            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return lstPersons;
    }

    private VideoDTO createVideo(JsonNode resultNode){
        String id = resultNode.get("id").asText();

        String videoPath = resultNode.get("key").asText();

        return new VideoDTO(id, videoPath);
    }

    @Override
    public List<VideoDTO> getVideo(String id, String type) {
        List<VideoDTO> lstVideos = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/" + type + "/" + id + "/videos?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {
                    String site = resultNode.get("site").asText();
                    if (Objects.equals(site, "YouTube")){
                        lstVideos.add(createVideo(resultNode));
                    }
                }

            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return lstVideos;
    }

    @Override
    public List<MovieShortDTO> getMovieCredits(String id) {
        List<MovieShortDTO> lstMovieShort = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/person/" + id + "/combined_credits?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = jsonNode.get("cast");

                for (JsonNode resultNode : resultsNode) {
                    lstMovieShort.add(getMovieShortFromJsonNode(resultNode));
                }


            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return lstMovieShort;
    }
}
