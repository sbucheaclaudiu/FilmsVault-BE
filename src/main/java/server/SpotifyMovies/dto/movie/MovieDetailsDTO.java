package server.SpotifyMovies.dto.movie;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MovieDetailsDTO {
    private String movieId;
    private String type;
    private String posterPath;
    private String backdropPath;
    private String movieName;
    private String overview;
    private String releaseDate;
    private String runtime;
    private Double rating;
    private Long budget;
    private Long revenue;
    private String genres;

    public MovieDetailsDTO(String movieId, String type, String posterPath, String backdropPath, String movieName, String overview, String releaseDate, String runtime, Double rating, Long budget, Long revenue, String genres) {
        this.movieId = movieId;
        this.type = type;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.movieName = movieName;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.rating = rating;
        this.budget = budget;
        this.revenue = revenue;
        this.genres = genres;
    }

}
