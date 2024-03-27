package server.SpotifyMovies.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.movie.MoviePlaylistDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.PlaylistMovie;
import server.SpotifyMovies.repository.MovieRepoInterface;
import server.SpotifyMovies.repository.PlaylistMoviesRepoInterface;
import server.SpotifyMovies.repository.PlaylistRepoInterface;
import server.SpotifyMovies.service.interfaces.PlaylistMoviesServiceInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlaylistMoviesService implements PlaylistMoviesServiceInterface {
    @Autowired
    private MovieRepoInterface movieRepo;

    @Autowired
    private PlaylistRepoInterface playlistRepo;

    @Autowired
    private PlaylistMoviesRepoInterface playlistMoviesRepo;

    @Override
    public List<MoviePlaylistDTO> getMoviesFromPlaylist(Long playlistId) throws ParseException {

        List<PlaylistMovie> playlistMovies = playlistMoviesRepo.findByPlaylistId(playlistId);

        Collections.sort(playlistMovies, Comparator.comparing(PlaylistMovie::getDateAddedToList).reversed());

        List<MoviePlaylistDTO> moviePlaylistDTOList = new ArrayList<>();

        for (PlaylistMovie playlistMovie : playlistMovies) {

            Movie movie = playlistMovie.getMovie();

            MoviePlaylistDTO moviePlaylistDTO = new MoviePlaylistDTO(movie.getId(), movie.getMovieName(), movie.getImagePath(), movie.getType(), movie.getGenres(), movie.getReleaseYear(), playlistMovie.getUserRating(), playlistMovie.getUserNote(), transformDateToString(playlistMovie.getDateAddedToList().toString().substring(0,10)), movie.getTmdbId());
            moviePlaylistDTOList.add(moviePlaylistDTO);
        }

        return moviePlaylistDTOList;
    }

    private String transformDateToString(String birthday) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormat.parse(birthday);

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = outputFormat.format(date);

        return formattedDate;
    }
}
