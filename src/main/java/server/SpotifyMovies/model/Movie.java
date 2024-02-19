package server.SpotifyMovies.model;

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
    private List<PlaylistMovie> playlistMovies;

    @Column
    private Long tmdbId;

    @Column
    private String movieName;

    @Column
    private long releaseYear;

    @Column
    private String genres;

    @Column
    private String userDescription;

    @Column
    private double userRating;

    @Column
    private String imagePath;

    public Movie(){}
}
