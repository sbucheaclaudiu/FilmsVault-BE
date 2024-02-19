package server.SpotifyMovies.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.SpotifyMovies.model.PlaylistMovie;
import server.SpotifyMovies.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreatePlaylistDTO {

    private String name;

    private String description;

    private String imagePath;

    private boolean privatePlaylist;

    private Long userId;
}
