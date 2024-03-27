package server.SpotifyMovies.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.movie.MovieDeleteDTO;
import server.SpotifyMovies.dto.movie.MoviePostDTO;
import server.SpotifyMovies.dto.ResponseDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.model.Movie;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.PlaylistMovie;
import server.SpotifyMovies.model.id.PlaylistMovieId;
import server.SpotifyMovies.repository.MovieRepoInterface;
import server.SpotifyMovies.repository.PlaylistMoviesRepoInterface;
import server.SpotifyMovies.repository.PlaylistRepoInterface;
import server.SpotifyMovies.service.interfaces.MovieServiceInterface;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovieService implements MovieServiceInterface {
    @Autowired
    private MovieRepoInterface movieRepo;

    @Autowired
    private PlaylistRepoInterface playlistRepo;

    @Autowired
    private PlaylistMoviesRepoInterface playlistMoviesRepo;

    private void isMovieInWatched(Long tmdbId, Playlist playlist) throws CustomException {
        Movie movie = movieRepo.findByTmdbId(tmdbId);

        if(movie == null){
            return;
        }

        Playlist watchedPlaylist = playlistRepo.findByUserIdAndName(playlist.getUser().getId(), "Watched");

        PlaylistMovieId playlistMovieId = new PlaylistMovieId(watchedPlaylist.getId(), movie.getId());

        if(playlistMoviesRepo.existsById(playlistMovieId)){
            throw new CustomException(movie.getMovieName() + " is in Watched!");
        }

    }

    private boolean removeFromWatchlist(Long tmdbId, Playlist playlist) throws CustomException {
        Movie movie = movieRepo.findByTmdbId(tmdbId);
        if(movie == null){
            return false;
        }

        Playlist watchedPlaylist = playlistRepo.findByUserIdAndName(playlist.getUser().getId(), "Watchlist");

        PlaylistMovieId playlistMovieId = new PlaylistMovieId(watchedPlaylist.getId(), movie.getId());


        if(playlistMoviesRepo.existsById(playlistMovieId)){
            playlistMoviesRepo.deleteById(playlistMovieId);
            return true;
        }

        return false;
    }

    private Playlist getPlaylistById(Long playlistId) throws CustomException {
        Playlist playlist;
        Optional<Playlist> optionalPlaylist = playlistRepo.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            playlist = optionalPlaylist.get();
        }
        else {
            throw new CustomException("Playlist not found");
        }

        return playlist;
    }

    @Override
    public ResponseDTO addMovieToPlaylist(MoviePostDTO moviePostDTO) throws CustomException {
        Playlist playlist = getPlaylistById(moviePostDTO.getPlaylistId());

        boolean removeFromWatchlist = false;
        if(Objects.equals(playlist.getName(), "Watched")){
            removeFromWatchlist = removeFromWatchlist(moviePostDTO.getMovieId(), playlist);
        }
        else if(Objects.equals(playlist.getName(), "Watchlist")){
            isMovieInWatched(moviePostDTO.getMovieId(), playlist);
        }

        if(playlist != null) {
            playlist.setLastTimeEdit(LocalDateTime.now());
            playlistRepo.save(playlist);
        }

        Movie movieDB = saveMovie(moviePostDTO);

        boolean savePlaylistMovie = savePlaylistMovie(playlist, movieDB, moviePostDTO.getUserNote(), moviePostDTO.getUserRating() );

        if(!savePlaylistMovie){
            return new ResponseDTO(true, moviePostDTO.getMovieName() + " allready in " + playlist.getName() + ". Note and rating changed.");
        }
        if(removeFromWatchlist)
            return new ResponseDTO(true, moviePostDTO.getMovieName() + " added in Watched. (removed from Watchlist)");

        return new ResponseDTO(true,  moviePostDTO.getMovieName() + " added in " + playlist.getName() + ".");
    }

    @Override
    public Movie saveMovie(MoviePostDTO moviePostDTO) {
        Movie movie;
        if(!movieRepo.existsByTmdbId(moviePostDTO.getMovieId())){
            movie = new Movie(moviePostDTO.getMovieId(), moviePostDTO.getMovieName(), moviePostDTO.getReleaseYear(), moviePostDTO.getGenres(), moviePostDTO.getMovieRating(), moviePostDTO.getPosterPath(), moviePostDTO.getType());
        }
        else{
            movie = movieRepo.findByTmdbId(moviePostDTO.getMovieId());
        }

        Movie movieDB = movieRepo.save(movie);;

        return movieDB;
    }

    @Override
    public boolean savePlaylistMovie(Playlist playlist, Movie movie, String userNote, double userRating) {
        PlaylistMovieId playlistMovieId = new PlaylistMovieId(playlist.getId(), movie.getId());

        boolean isNotSaved = true;
        if (playlistMoviesRepo.existsById(playlistMovieId)) {
            isNotSaved = false;
        }

        PlaylistMovie playlistMovie = new PlaylistMovie(playlistMovieId, playlist, movie, LocalDateTime.now(), userNote, userRating * 2);

        playlistMoviesRepo.save(playlistMovie);
        return isNotSaved;
    }

    @Override
    public void removeMovieFromPlaylist(MovieDeleteDTO movie) {
        PlaylistMovieId playlistMovieId = new PlaylistMovieId(movie.getPlaylistId(), movie.getMovieId());

        if(playlistMoviesRepo.existsById(playlistMovieId)){
            playlistMoviesRepo.deleteById(playlistMovieId);
        }
    }
}
