package server.SpotifyMovies.model.id;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import server.SpotifyMovies.model.User;

import java.io.Serializable;

@Embeddable
public class FollowersId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "followedUser")
    private User folloersUser;

    public FollowersId(User user, User folloersUser) {
        this.user = user;
        this.folloersUser = folloersUser;
    }

    public FollowersId() {

    }
}
