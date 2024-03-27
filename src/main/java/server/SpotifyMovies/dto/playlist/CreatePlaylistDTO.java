package server.SpotifyMovies.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreatePlaylistDTO {
    private String name;
    private String description;
    private String imagePath;
    private boolean privatePlaylist;
    private Long userId;
}
