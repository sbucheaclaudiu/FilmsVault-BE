package server.SpotifyMovies.dto.person;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PersonShortDTO {
    private String personId;
    private String name;
    private String profilePath;

    public PersonShortDTO(String personId, String name, String profilePath) {
        this.personId = personId;
        this.name = name;
        this.profilePath = profilePath;
    }
}
