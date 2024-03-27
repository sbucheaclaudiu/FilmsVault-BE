package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.movie.MoviePlaylistDTO;
import server.SpotifyMovies.exceptions.CustomException;

import java.text.ParseException;
import java.util.List;

public interface PlaylistMoviesServiceInterface {
    List<MoviePlaylistDTO> getMoviesFromPlaylist(Long playlistId) throws CustomException, ParseException;
}
