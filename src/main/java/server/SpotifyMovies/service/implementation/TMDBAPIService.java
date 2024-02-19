package server.SpotifyMovies.service.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.MovieShortDTO;
import server.SpotifyMovies.service.interfaces.TMDBAPIServiceInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class TMDBAPIService implements TMDBAPIServiceInterface {
    private final String apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjNhMzgzMDM2YjZjMWFhOWI0YmNmMzY0YWZkOTRkMyIsInN1YiI6IjY1NzM1ZDBiMDA2YjAxMDBlMWE5N2JhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PaamffUcloAVp0SaezXIMBzXUhnXD0Lrv-gNLZ1huYs";

    private ObjectMapper objectMapper = new ObjectMapper();


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

        String title = "";
        try {
            title = resultNode.get("title").asText();
        } catch (Exception e) {

            title = resultNode.get("name").asText();
        }

        String posterPath = "";
        try {
            posterPath = "https://image.tmdb.org/t/p/original" + resultNode.get("poster_path").asText();
        } catch (Exception e) {
            posterPath = "https://image.tmdb.org/t/p/original" + resultNode.get("profile_path").asText();
        }

        return new MovieShortDTO(id, title, posterPath);
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
}
