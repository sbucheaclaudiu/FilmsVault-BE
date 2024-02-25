package server.SpotifyMovies.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PersonDetailsDTO {
    private String personId;
    private String type;
    private String name;
    private String profilePath;
    private String biography;
    private String birthday;
    private String placeOfBirth;
    private String backdropPath;

    public PersonDetailsDTO(String personId, String type, String name, String profilePath, String biography, String birthday, String placeOfBirth) {
        this.personId = personId;
        this.type = type;
        this.name = name;
        this.profilePath = profilePath;
        this.biography = biography;
        this.birthday = birthday;
        this.placeOfBirth = placeOfBirth;
    }
}
