package server.SpotifyMovies.dto.movie;


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
    private String genres;
    private String releaseDate;
    private double rating;

    public MovieShortDTO(String tmdbId, String movieName, String posterPath, String type, String genres, String releaseDate, double rating) {
        this.movieId = tmdbId;
        this.movieName = movieName;
        this.posterPath = posterPath;
        this.type = type;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }
}
