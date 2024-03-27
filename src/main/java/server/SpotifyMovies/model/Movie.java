package server.SpotifyMovies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "Movies")
@AllArgsConstructor
@Getter
@Setter
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<PlaylistMovie> playlistMovies;

    @Column
    private Long tmdbId;

    @Column
    private String movieName;

    @Column
    private String releaseYear;

    @Column
    private String genres;

    @Column
    private double movieRating;

    @Column
    private String imagePath;

    @Column
    private String type;

    public Movie(){}

    public Movie(Long tmdbId, String movieName, String releaseYear, String genres, double movieRating, String imagePath, String type) {
        this.tmdbId = tmdbId;
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.movieRating = movieRating;
        this.imagePath = imagePath;
        this.type = type;
    }
}
