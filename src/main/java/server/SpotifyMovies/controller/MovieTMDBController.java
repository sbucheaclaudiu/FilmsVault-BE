package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.movie.MovieDetailsDTO;
import server.SpotifyMovies.dto.movie.TVDetailsDTO;
import server.SpotifyMovies.dto.movie.VideoDTO;
import server.SpotifyMovies.service.interfaces.MovieTMDBServiceInterface;

import java.util.List;

@Controller
@RequestMapping("/moviesVault/details")
@CrossOrigin
public class MovieTMDBController {
    @Autowired
    private MovieTMDBServiceInterface movieTMDBServiceInterface;

    @GetMapping("/getMovieDetails")
    public ResponseEntity<MovieDetailsDTO> getMovieDetails(@RequestParam String id){
        try{
            MovieDetailsDTO movieDetailsDTO = movieTMDBServiceInterface.getMovieDetails(id);

            return ResponseEntity.ok(movieDetailsDTO);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getTVDetails")
    public ResponseEntity<TVDetailsDTO> getTVDetails(@RequestParam String id){
        try{
            TVDetailsDTO tvDetailsDTO = movieTMDBServiceInterface.getTVDetails(id);

            return ResponseEntity.ok(tvDetailsDTO);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getVideos")
    public ResponseEntity<List<VideoDTO>> getVideos(@RequestParam String id, @RequestParam String type){
        try{
            List<VideoDTO> lstVideoDTO = movieTMDBServiceInterface.getVideo(id, type);

            return ResponseEntity.ok( lstVideoDTO.subList(0, Math.min(lstVideoDTO.size(), 6)));
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(null);
        }
    }

}
