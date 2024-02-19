package server.SpotifyMovies.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import server.SpotifyMovies.model.Playlist;

import java.io.Serializable;

@Embeddable
public class PlaylistMovieId implements Serializable {

    @Column(name="playlist_id")
    private Long playlistId;

    @Column(name="movie_id")
    private Long movieId;

    public PlaylistMovieId() {}

    public PlaylistMovieId(Long playlistId, Long movieId) {
        this.playlistId = playlistId;
        this.movieId = movieId;
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
