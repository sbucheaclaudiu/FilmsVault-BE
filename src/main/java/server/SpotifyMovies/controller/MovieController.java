package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.*;
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

    @GetMapping("/getTVDetails")
    public ResponseEntity<TVDetailsDTO> getTVDetails(@RequestParam String id){
        try{
            TVDetailsDTO tvDetailsDTO = movieService.getTVDetails(id);

            return ResponseEntity.ok(tvDetailsDTO);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getPersonDetails")
    public ResponseEntity<PersonDetailsDTO> getPersonDetails(@RequestParam String id){
        try{
            PersonDetailsDTO personDetailsDTO = movieService.getPersonDetails(id);

            return ResponseEntity.ok(personDetailsDTO);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getCast")
    public ResponseEntity<List<PersonShortDTO>> getCast(@RequestParam String id, @RequestParam String type){
        try{
            List<PersonShortDTO> movieDetailsDTO = movieService.getCast(id, type);

            return ResponseEntity.ok(movieDetailsDTO);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getVideos")
    public ResponseEntity<List<VideoDTO>> getVideos(@RequestParam String id, @RequestParam String type){
        try{
            List<VideoDTO> lstVideoDTO = movieService.getVideo(id, type);

            return ResponseEntity.ok( lstVideoDTO.subList(0, Math.min(lstVideoDTO.size(), 6)));
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getMovieCredits")
    public ResponseEntity<List<MovieShortDTO>> getMovieCredits(@RequestParam String id){
        try{
            List<MovieShortDTO> lstMovieShort = movieService.getMovieCredits(id);

            return ResponseEntity.ok(lstMovieShort);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }


}
