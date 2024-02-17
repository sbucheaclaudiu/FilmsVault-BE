package server.SpotifyMovies.service.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.dto.MovieShortDTO;
import server.SpotifyMovies.repository.MovieRepo;
import server.SpotifyMovies.service.interfaces.MovieAPIServiceInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieAPIService implements MovieAPIServiceInterface {
    @Autowired
    private MovieRepo listMovieRepoInterface;

    private final String apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjNhMzgzMDM2YjZjMWFhOWI0YmNmMzY0YWZkOTRkMyIsInN1YiI6IjY1NzM1ZDBiMDA2YjAxMDBlMWE5N2JhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PaamffUcloAVp0SaezXIMBzXUhnXD0Lrv-gNLZ1huYs";

    @Override
    public List<Movie> getAllListMovies() {
        return listMovieRepoInterface.findAll();
    }

    private HttpResponse<String> apiCall(String apiUrl){

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .setHeader("accept", "application/json")
                    .setHeader("Authorization", apiKey)
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MovieShortDTO> searchMultiByName(String name){
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/search/multi?query=" + name + "&include_adult=false&language=en-US&page=1";

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    String id = resultNode.get("id").asText();
                    String title = "";
                    try{
                        title = resultNode.get("title").asText();
                    }catch (Exception e){

                        title = resultNode.get("name").asText();
                    }

                    String posterPath = "";
                    try{
                        posterPath = "https://image.tmdb.org/t/p/original/" + resultNode.get("poster_path").asText();
                    }catch (Exception e){
                        posterPath = "https://image.tmdb.org/t/p/original/" + resultNode.get("profile_path").asText();
                    }

                    MovieShortDTO movieExplore = new MovieShortDTO(id, title, posterPath);
                    lstPopularMovies.add(movieExplore);
                }

                return lstPopularMovies;
            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public List<MovieShortDTO> getPopularMovies(int noPage) {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=" + noPage;

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    String id = resultNode.get("id").asText();
                    String title = resultNode.get("title").asText();
                    int releaseYear = Integer.parseInt(resultNode.get("release_date").asText().substring(0, 4));
                    String posterPath = "https://image.tmdb.org/t/p/original/" + resultNode.get("poster_path").asText();
                    double rating = BigDecimal.valueOf(resultNode.get("vote_average").asDouble()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

                    MovieShortDTO movieExplore = new MovieShortDTO(id, title, releaseYear, rating, posterPath);
                    lstPopularMovies.add(movieExplore);
                }

                return lstPopularMovies;
            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MovieShortDTO> getTrendingAll() {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/trending/all/day?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    String id = resultNode.get("id").asText();
                    String title = "";
                    try{
                        title = resultNode.get("title").asText();
                    }catch (Exception e){

                        title = resultNode.get("name").asText();
                    }

                    String posterPath = "";
                    try{
                        posterPath = "https://image.tmdb.org/t/p/original" + resultNode.get("poster_path").asText();
                    }catch (Exception e){
                        posterPath = "https://image.tmdb.org/t/p/original" + resultNode.get("profile_path").asText();
                    }

                    MovieShortDTO movieExplore = new MovieShortDTO(id, title, posterPath);
                    lstPopularMovies.add(movieExplore);
                }

                return lstPopularMovies;
            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MovieShortDTO> getTrendingMovies() {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/trending/movie/day?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    String id = resultNode.get("id").asText();
                    String title = resultNode.get("title").asText();
                    int releaseYear = Integer.parseInt(resultNode.get("release_date").asText().substring(0, 4));
                    String posterPath = "https://image.tmdb.org/t/p/original/" + resultNode.get("poster_path").asText();
                    double rating = resultNode.get("vote_average").asDouble();

                    MovieShortDTO movieExplore = new MovieShortDTO(id, title, releaseYear, rating, posterPath);
                    lstPopularMovies.add(movieExplore);
                }

                return lstPopularMovies;
            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MovieShortDTO> getTrendingTvShows() {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/trending/tv/day?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    String id = resultNode.get("id").asText();
                    String title = resultNode.get("name").asText();
                    int releaseYear = Integer.parseInt(resultNode.get("first_air_date").asText().substring(0, 4));
                    String posterPath = "https://image.tmdb.org/t/p/original/" + resultNode.get("poster_path").asText();
                    double rating = resultNode.get("vote_average").asDouble();

                    MovieShortDTO movieExplore = new MovieShortDTO(id, title, releaseYear, rating, posterPath);
                    lstPopularMovies.add(movieExplore);
                }

                return lstPopularMovies;
            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MovieShortDTO> upcoming(int noPage) {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=" + noPage;

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    String id = resultNode.get("id").asText();
                    String title = resultNode.get("title").asText();
                    int releaseYear = Integer.parseInt(resultNode.get("release_date").asText().substring(0, 4));
                    String posterPath = "https://image.tmdb.org/t/p/original/" + resultNode.get("poster_path").asText();
                    double rating = resultNode.get("vote_average").asDouble();

                    MovieShortDTO movieExplore = new MovieShortDTO(id, title, releaseYear, rating, posterPath);
                    lstPopularMovies.add(movieExplore);
                }

                return lstPopularMovies;
            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MovieShortDTO> topRatedMovies(int noPage) {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=" + noPage;

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    String id = resultNode.get("id").asText();
                    String title = resultNode.get("title").asText();
                    int releaseYear = Integer.parseInt(resultNode.get("release_date").asText().substring(0, 4));
                    String posterPath = "https://image.tmdb.org/t/p/original/" + resultNode.get("poster_path").asText();
                    double rating = resultNode.get("vote_average").asDouble();

                    MovieShortDTO movieExplore = new MovieShortDTO(id, title, releaseYear, rating, posterPath);
                    lstPopularMovies.add(movieExplore);
                }

                return lstPopularMovies;
            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MovieShortDTO> topRatedTvShows(int noPage) {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        String apiUrl = "https://api.themoviedb.org/3/tv/top_rated?language=en-US&page=" + noPage;

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    String id = resultNode.get("id").asText();
                    String title = resultNode.get("name").asText();
                    int releaseYear = Integer.parseInt(resultNode.get("first_air_date").asText().substring(0, 4));
                    String posterPath = "https://image.tmdb.org/t/p/original/" + resultNode.get("poster_path").asText();
                    double rating = resultNode.get("vote_average").asDouble();

                    MovieShortDTO movieExplore = new MovieShortDTO(id, title, releaseYear, rating, posterPath);
                    lstPopularMovies.add(movieExplore);
                }

                return lstPopularMovies;
            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
