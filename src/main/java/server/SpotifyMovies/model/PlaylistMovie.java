package server.SpotifyMovies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import server.SpotifyMovies.model.id.PlaylistMovieId;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "Playlist_movies")
public class PlaylistMovie implements Serializable {
    @EmbeddedId
    PlaylistMovieId id;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlist_id")
    Playlist playlist;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    Movie movie;


    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateAddedToList;

    @Column
    private String userNote;

    @Column
    private double userRating;


    public PlaylistMovie() {}

    public PlaylistMovie(PlaylistMovieId id, Playlist playlist, Movie movie, LocalDateTime dateAddedToList, String userNote, double userRating) {
        this.id = id;
        this.playlist = playlist;
        this.movie = movie;
        this.dateAddedToList = dateAddedToList;
        this.userNote = userNote;
        this.userRating = userRating;
    }

}
