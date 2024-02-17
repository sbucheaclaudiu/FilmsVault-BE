package server.SpotifyMovies.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import server.SpotifyMovies.model.ListDetails;

import java.io.Serializable;

@Embeddable
public class ListMovieId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "listDetailsId")
    private ListDetails listDetails;

    @Column(name = "movieId")
    private Long movieId;

    public ListMovieId(ListDetails listDetails, Long movieId) {
        this.listDetails = listDetails;
        this.movieId = movieId;
    }

    public ListMovieId() {}
}
