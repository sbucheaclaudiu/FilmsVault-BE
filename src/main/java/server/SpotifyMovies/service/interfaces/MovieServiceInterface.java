package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.movie.MovieDeleteDTO;
import server.SpotifyMovies.dto.movie.MoviePostDTO;
import server.SpotifyMovies.dto.ResponseDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.Playlist;

public interface MovieServiceInterface {
    ResponseDTO addMovieToPlaylist(MoviePostDTO movie) throws CustomException;

    Movie saveMovie(MoviePostDTO movie);

    boolean savePlaylistMovie(Playlist playlist, Movie movie, String userNote, double userRating);

    void removeMovieFromPlaylist(MovieDeleteDTO movie);
}
