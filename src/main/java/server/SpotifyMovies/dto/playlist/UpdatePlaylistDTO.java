package server.SpotifyMovies.dto.playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlaylistDTO {
    private Long playlistId;
    private String name;
    private String description;
    private String imagePath;
    private boolean privatePlaylist;
    private Long userId;
}
