package server.SpotifyMovies.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MovieShortDTO {

    private String movieId;
    private String movieName;
    private String posterPath;
    private String type;

    public MovieShortDTO(String movieId, String movieName, String posterPath, String type) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.posterPath = posterPath;
        this.type = type;
    }

}
