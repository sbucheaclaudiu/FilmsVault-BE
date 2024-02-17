package server.SpotifyMovies.model;

import jakarta.persistence.*;
import lombok.Data;
import server.SpotifyMovies.model.id.ListMovieId;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "movies_list")
public class Movie implements Serializable {
    @EmbeddedId
    private ListMovieId listMoviesId;

    @Column
    private String id;

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

    @Column
    private double rating;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dateAddedToList;


    public Movie(){}

    public ListMovieId getListMoviesId() {
        return listMoviesId;
    }

    public void setListMoviesId(ListMovieId listMoviesId) {
        this.listMoviesId = listMoviesId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public long getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(long releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public double getImdbRating() {
        return rating;
    }

    public void setImdbRating(double imdbRating) {
        this.rating = imdbRating;
    }

    public LocalDate getDateAddedToList() {
        return dateAddedToList;
    }

    public void setDateAddedToList(LocalDate dateAddedToList) {
        this.dateAddedToList = dateAddedToList;
    }
}
