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
@AllArgsConstructor
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

    public PlaylistMovie() {

    }
}
