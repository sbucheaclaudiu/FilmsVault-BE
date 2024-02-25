package server.SpotifyMovies.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TVDetailsDTO {
    private String movieId;
    private String type;
    private String backdropPath;
    private String posterPath;
    private String movieName;
    private String overview;
    private String firstAirDate;
    private String lastAirDate;
    private String noofSeasons;
    private String noOfEpisods;
    private double rating;
    private String genres;

    public TVDetailsDTO(String id, String type, String backdropPath, String posterPath, String name, String overview, String firstAirDate, String lastAirDate, String noofSeasons, String noOfEpisods, double rating, String genres) {
        this.movieId = id;
        this.type = type;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.movieName = name;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.noofSeasons = noofSeasons;
        this.noOfEpisods = noOfEpisods;
        this.rating = rating;
        this.genres = genres;
    }
}
