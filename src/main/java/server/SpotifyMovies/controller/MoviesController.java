package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.model.dto.MovieShortDTO;
import server.SpotifyMovies.service.interfaces.MovieAPIServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/spotifyMovies/resource")
@CrossOrigin
public class MoviesController {
    @Autowired
    private MovieAPIServiceInterface movieService;

    @GetMapping("/searchMulti")
    public ResponseEntity<List<MovieShortDTO>> searchMulti(@RequestParam String name) {
        List<MovieShortDTO> lstPopularMovies = movieService.searchMultiByName(name);

        return ResponseEntity.ok(lstPopularMovies.subList(0, Math.min(lstPopularMovies.size(), 12)));
    }

    @GetMapping("/popularMovies")
    public ResponseEntity<List<MovieShortDTO>> popularMovies() {
        List<MovieShortDTO> lstPopularMovies = movieService.getPopularMovies(1);

        return ResponseEntity.ok(lstPopularMovies);
    }

    @GetMapping("/trendingAll")
    public ResponseEntity<List<MovieShortDTO>> trendingAll() {
        List<MovieShortDTO> lstPopularMovies = movieService.getTrendingAll();

        return ResponseEntity.ok(lstPopularMovies);
    }

    @GetMapping("/trendingMovies")
    public ResponseEntity<List<MovieShortDTO>> trendingMovies() {
        List<MovieShortDTO> lstTrendingMovies = movieService.getTrendingMovies();

        return ResponseEntity.ok(lstTrendingMovies);
    }

    @GetMapping("/trendingTvShows")
    public ResponseEntity<List<MovieShortDTO>> trendingTvShows() {
        List<MovieShortDTO> trendingTvShows = movieService.getTrendingTvShows();


        return ResponseEntity.ok(trendingTvShows);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieShortDTO>> upcoming() {
        List<MovieShortDTO> trendingTvShows = movieService.upcoming(1);


        return ResponseEntity.ok(trendingTvShows);
    }

    @GetMapping("/topRatedMovies")
    public ResponseEntity<List<MovieShortDTO>> topRatedMovies() {
        Random random = new Random();

        int noPage = random.nextInt(5) + 1;

        List<MovieShortDTO> trendingTvShows = movieService.topRatedMovies(noPage);

        return ResponseEntity.ok(trendingTvShows);
    }

    @GetMapping("/topRatedTvShows")
    public ResponseEntity<List<MovieShortDTO>> topRatedTvShows() {
        Random random = new Random();

        int noPage = random.nextInt(5) + 1;

        List<MovieShortDTO> trendingTvShows = movieService.topRatedTvShows(noPage);

        return ResponseEntity.ok(trendingTvShows);
    }
}
