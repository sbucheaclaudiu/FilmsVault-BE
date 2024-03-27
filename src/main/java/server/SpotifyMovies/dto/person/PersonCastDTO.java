package server.SpotifyMovies.dto.person;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PersonCastDTO {
    private String personId;
    private String name;
    private String profilePath;
    private String character;

    public PersonCastDTO(String personId, String name, String profilePath, String character) {
        this.personId = personId;
        this.name = name;
        this.profilePath = profilePath;
        this.character = character;
    }
}
