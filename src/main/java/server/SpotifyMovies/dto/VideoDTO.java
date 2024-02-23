package server.SpotifyMovies.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class VideoDTO {
    private String videoId;
    private String videoPath;

    public VideoDTO(String videoId, String videoPath) {
        this.videoId = videoId;
        this.videoPath = videoPath;
    }
}
