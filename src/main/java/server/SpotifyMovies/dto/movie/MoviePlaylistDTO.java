package server.SpotifyMovies.dto.movie;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class MoviePlaylistDTO {
    private Long movieId;
    private String movieName;
    private String posterPath;
    private String type;
    private String genres;
    private String releaseDate;
    private double userRating;
    private String userNote;
    private String dateAdded;

    public MoviePlaylistDTO(Long movieId, String movieName, String posterPath, String type, String genres, String releaseDate, double userRating, String userNote, String dateAdded) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.posterPath = posterPath;
        this.type = type;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.userNote = userNote;
        this.dateAdded = dateAdded;
    }
}
