package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.CreatePlaylistDTO;
import server.SpotifyMovies.dto.MovieDetailsDTO;
import server.SpotifyMovies.dto.PersonShortDTO;
import server.SpotifyMovies.dto.VideoDTO;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.service.interfaces.TMDBAPIServiceInterface;

import java.util.List;

@Controller
@RequestMapping("/moviesVault/details")
@CrossOrigin
public class MovieController {
    @Autowired
    private TMDBAPIServiceInterface movieService;

    @GetMapping("/getMovieDetails")
    public ResponseEntity<MovieDetailsDTO> getMovieDetails(@RequestParam String id){
        try{
            MovieDetailsDTO movieDetailsDTO = movieService.getMovieDetails(id);

            return ResponseEntity.ok(movieDetailsDTO);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getMovieCast")
    public ResponseEntity<List<PersonShortDTO>> getMovieCast(@RequestParam String id){
        try{
            List<PersonShortDTO> movieDetailsDTO = movieService.getMovieCast(id);

            return ResponseEntity.ok(movieDetailsDTO);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getMovieVideos")
    public ResponseEntity<List<VideoDTO>> getMovieVideos(@RequestParam String id){
        try{
            List<VideoDTO> lstVideoDTO = movieService.getMovieVideo(id);

            return ResponseEntity.ok( lstVideoDTO.subList(0, Math.min(lstVideoDTO.size(), 6)));
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }


}
