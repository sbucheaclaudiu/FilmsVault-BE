package server.SpotifyMovies.service.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.movie.MovieShortDTO;
import server.SpotifyMovies.dto.person.PersonCastDTO;
import server.SpotifyMovies.dto.person.PersonDetailsDTO;
import server.SpotifyMovies.dto.person.PersonShortDTO;
import server.SpotifyMovies.mapper.JsonNodeToDTO;
import server.SpotifyMovies.mapper.JsonNodeToDTOInterface;
import server.SpotifyMovies.service.interfaces.PersonTMDBServiceInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PersonTMDBService implements PersonTMDBServiceInterface {
    private final String apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjNhMzgzMDM2YjZjMWFhOWI0YmNmMzY0YWZkOTRkMyIsInN1YiI6IjY1NzM1ZDBiMDA2YjAxMDBlMWE5N2JhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PaamffUcloAVp0SaezXIMBzXUhnXD0Lrv-gNLZ1huYs";
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonNodeToDTOInterface jsonMapper = new JsonNodeToDTO();
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
            System.out.println(e.getMessage());
        }

        return null;
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

                int noPage = random.nextInt(noOfResults);
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
    public PersonDetailsDTO getPersonDetails(String id) throws ParseException {
        String apiUrl = "https://api.themoviedb.org/3/person/" + id + "?language=en-US";

        try {
            HttpResponse<String> response = apiCall(apiUrl);
            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                PersonDetailsDTO person = jsonMapper.createPersonDetails(jsonNode);
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

    @Override
    public List<PersonShortDTO> getPopularPersons(String apiUrl) {
        List<PersonShortDTO> lstPerson = new ArrayList<>();

        try {
            HttpResponse<String> response = apiCall(apiUrl);

            if (response != null && response.statusCode() == 200) {
                String jsonResponse = response.body();

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = jsonNode.get("results");

                System.out.println("ok");
                //System.out.println(resultsNode);
                for (JsonNode resultNode : resultsNode) {
                    System.out.println(resultNode);
                    lstPerson.add(jsonMapper.createPersonShort(resultNode));
                }
                System.out.println("ok2");

            } else {
                System.out.println("Eroare la efectuarea request-ului. Codul de stare: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Exceptie la api: " + e.getMessage());
        }

        return lstPerson;
    }

    @Override
    public List<PersonCastDTO> getCast(String id, String type) {
        List<PersonCastDTO> lstPersons = new ArrayList<>();
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
                        lstPersons.add(jsonMapper.createPersonCast(resultNode));
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
                    lstMovieShort.add(jsonMapper.createMovieShort(resultNode));
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
