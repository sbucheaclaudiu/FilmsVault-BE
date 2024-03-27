package server.SpotifyMovies.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MoviePostDTO {
    private Long movieId;
    private String movieName;
    private String posterPath;
    private String genres;
    private String userNote;
    private String releaseYear;
    private double userRating;
    private double movieRating;
    private Long playlistId;
    private String type;
}
