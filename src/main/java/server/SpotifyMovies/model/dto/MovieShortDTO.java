package server.SpotifyMovies.model.dto;


public class MovieShortDTO {

    private String movieId;
    private String movieName;
    private int releaseYear;
    private double rating;
    private String posterPath;
    private String type;

    public MovieShortDTO(String movieId, String movieName, int releaseYear, double rating, String posterPath) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.posterPath = posterPath;
    }

    public MovieShortDTO(String movieId, String movieName, String posterPath) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.posterPath = posterPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "MovieExploreDTO{" +
                "movieId='" + movieId + '\'' +
                ", movieName='" + movieName + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }
}
