package server.SpotifyMovies.model.id;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import server.SpotifyMovies.model.User;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class FollowersId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "followedUser")
    private User followedUser;

    public FollowersId(User user, User followedUser) {
        this.user = user;
        this.followedUser = followedUser;
    }

    public FollowersId() {

    }
}
