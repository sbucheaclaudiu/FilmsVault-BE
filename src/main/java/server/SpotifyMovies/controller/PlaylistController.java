package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.movie.MoviePlaylistDTO;
import server.SpotifyMovies.dto.playlist.CreatePlaylistDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.service.interfaces.PlaylistMoviesServiceInterface;
import server.SpotifyMovies.service.interfaces.PlaylistServiceInterface;
import server.SpotifyMovies.service.interfaces.UserServiceInterface;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/moviesVault/playlist")
@CrossOrigin
public class PlaylistController {
    @Autowired
    private PlaylistServiceInterface playlistService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private PlaylistMoviesServiceInterface playlistMoviesService;

    @GetMapping("/getPlaylistsByUser")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsByUser(@RequestParam Long userId) {
        try{
            List<PlaylistDTO> lstUserPlaylist = playlistService.getPlaylistsByUser(userId);
            return ResponseEntity.ok(lstUserPlaylist);
        } catch (Exception exception){
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/getPlaylistsByUserPublic")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsByUserPublic(@RequestParam Long userId) {
        try{
            List<PlaylistDTO> lstUserPlaylist = playlistService.getPlaylistsByUserPublic(userId);
            return ResponseEntity.ok(lstUserPlaylist);
        } catch (Exception exception){
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/getPlaylistById")
    public ResponseEntity<PlaylistDTO> getPlaylistsById(@RequestParam Long id) {
        try{
            PlaylistDTO playlist = playlistService.getPlaylistById(id);
            return ResponseEntity.ok(playlist);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping("/createPlaylist")
    public ResponseEntity<String> createPlaylist(@RequestBody CreatePlaylistDTO playlist){
        try{
            User user = userService.getUserById(playlist.getUserId());
            playlistService.createPlaylist(playlist, user);

            return ResponseEntity.ok("Playlist created.");
        } catch (Exception exception){
            return ResponseEntity.ok("Failed to create playlist.");
        }
    }

    @GetMapping("/getMoviesFromPlaylist")
    public ResponseEntity<List<MoviePlaylistDTO>> getMovieFromPlaylist(@RequestParam Long playlistId){
        try{
            List<MoviePlaylistDTO> playlistMoviesLst = playlistMoviesService.getMoviesFromPlaylist(playlistId);

            return ResponseEntity.ok(playlistMoviesLst);
        } catch (Exception exception){
            return ResponseEntity.ok(new ArrayList<MoviePlaylistDTO>());
        }
    }
}
