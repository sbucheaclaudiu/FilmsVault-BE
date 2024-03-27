package server.SpotifyMovies.dto.playlist;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class PlaylistDTO {
    private Long id;
    private String name;
    private String playlistUsername;
    private String imagePath;
    private String lastTimeEdit;
    private String dateCreated;
    private Boolean privatePlaylist;
    private String description;

    public PlaylistDTO(Long id, String name, String playlistUsername, String imagePath, String lastTimeEdit, String dateCreated, Boolean privatePlaylist, String description) {
        this.id = id;
        this.name = name;
        this.playlistUsername = playlistUsername;
        this.imagePath = imagePath;
        this.lastTimeEdit = lastTimeEdit;
        this.dateCreated = dateCreated;
        this.privatePlaylist = privatePlaylist;
        this.description = description;
    }
}
