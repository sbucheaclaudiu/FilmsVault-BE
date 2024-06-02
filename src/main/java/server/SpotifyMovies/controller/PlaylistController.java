package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.movie.MoviePlaylistDTO;
import server.SpotifyMovies.dto.playlist.CreatePlaylistDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.dto.playlist.UpdatePlaylistDTO;
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
            System.out.println(exception.getMessage());
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
            System.out.println(exception.getMessage());
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
            System.out.println(exception.getMessage());
            return ResponseEntity.ok("Failed to create playlist.");
        }
    }

    @PostMapping("/updatePlaylist")
    public ResponseEntity<String> updatePlaylist(@RequestBody UpdatePlaylistDTO playlist){
        try{
            System.out.println("ok");
            User user = userService.getUserById(playlist.getUserId());
            playlistService.updatePlaylist(playlist, user);

            return ResponseEntity.ok("Playlist updated.");
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok("Failed to update playlist.");
        }
    }

    @GetMapping("/getMoviesFromPlaylist")
    public ResponseEntity<List<MoviePlaylistDTO>> getMovieFromPlaylist(@RequestParam Long playlistId){
        try{
            List<MoviePlaylistDTO> playlistMoviesLst = playlistMoviesService.getMoviesFromPlaylist(playlistId);

            return ResponseEntity.ok(playlistMoviesLst);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(new ArrayList<MoviePlaylistDTO>());
        }
    }

    @GetMapping("/deletePlaylist")
    public ResponseEntity<Boolean> deletePlaylist(@RequestParam Long playlistId){
        try{
            playlistService.deletePlaylist(playlistId);
            return ResponseEntity.ok(true);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/getRandomPlaylists")
    public ResponseEntity<List<PlaylistDTO>> getRandomPlaylists(@RequestParam Long userId) {
        try{
            List<PlaylistDTO> lstUserPlaylist = playlistService.getRandomPlaylists(userId);
            return ResponseEntity.ok(lstUserPlaylist);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/getPlaylistsByName")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsByName(@RequestParam String name) {
        try{
            List<PlaylistDTO> lstUserPlaylist = playlistService.getPlaylistByName(name);
            return ResponseEntity.ok(lstUserPlaylist);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}
