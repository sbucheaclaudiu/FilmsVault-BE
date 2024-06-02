package server.SpotifyMovies.service.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.movie.MovieDetailsDTO;
import server.SpotifyMovies.dto.movie.MovieShortDTO;
import server.SpotifyMovies.dto.movie.TVDetailsDTO;
import server.SpotifyMovies.dto.movie.VideoDTO;
import server.SpotifyMovies.mapper.JsonNodeToDTO;
import server.SpotifyMovies.mapper.JsonNodeToDTOInterface;
import server.SpotifyMovies.service.interfaces.MovieTMDBServiceInterface;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class MovieTMDBService implements MovieTMDBServiceInterface {
    private final String apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjNhMzgzMDM2YjZjMWFhOWI0YmNmMzY0YWZkOTRkMyIsInN1YiI6IjY1NzM1ZDBiMDA2YjAxMDBlMWE5N2JhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PaamffUcloAVp0SaezXIMBzXUhnXD0Lrv-gNLZ1huYs";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JsonNodeToDTOInterface jsonMapper = new JsonNodeToDTO();
    private final Random random = new Random();

    private static final String pythonServerURL = "http://127.0.0.1:5000/recommend";


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

    @Override
    public List<MovieShortDTO> searchMultiByName(String name) {
        List<MovieShortDTO> lstPopularMovies = new ArrayList<>();
        name = name.replace(" ", "%20");
        String apiUrl = "https://api.themoviedb.org/3/search/multi?query=" + name + "&include_adult=false&language=en-US&page=1";

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = jsonNode.get("results");

                for (JsonNode resultNode : resultsNode) {

                    lstPopularMovies.add(jsonMapper.createMovieShort(resultNode));
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
                    lstPopularMovies.add(jsonMapper.createMovieShort(resultNode));
                }

            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return lstPopularMovies;
    }

    private List<String> getRecommendationsForMovie(String title) throws IOException {
        String query = String.format("?title=%s", java.net.URLEncoder.encode(title, "UTF-8"));
        URL url = new URL(pythonServerURL + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + connection.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        connection.disconnect();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(sb.toString());
        JsonNode recommendationsNode = jsonNode.get("recommendations");

        List<String> recommendations = new ArrayList<>();
        if (recommendationsNode != null && recommendationsNode.isArray()) {
            for (JsonNode node : recommendationsNode) {
                recommendations.add(node.asText());

            }
        }

        return recommendations;
    }

    @Override
    public List<MovieShortDTO> getRecommendations(String title) throws IOException {
        List<String> lstRecommendationsTitle = getRecommendationsForMovie(title);

        List<MovieShortDTO> lstMoviesRecommedations = new ArrayList<>();

        for (String movieTitle : lstRecommendationsTitle){
            List<MovieShortDTO> results = searchMultiByName(movieTitle);
            if (results != null && !results.isEmpty()) {
                MovieShortDTO movie =  results.get(0);
                lstMoviesRecommedations.add(movie);
            }
        }

        return lstMoviesRecommedations;
    }

    @Override
    public MovieDetailsDTO getMovieDetails(String id) {
        System.out.println(id);
        String apiUrl = "https://api.themoviedb.org/3/movie/" + id + "?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                return jsonMapper.createMovieDetails(jsonNode);

            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return null;
    }

    @Override
    public TVDetailsDTO getTVDetails(String id) {
        String apiUrl = "https://api.themoviedb.org/3/tv/" + id + "?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                return jsonMapper.createTVDetails(jsonNode);

            }
            else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return null;
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
                        lstVideos.add(jsonMapper.createVideo(resultNode));
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

}
