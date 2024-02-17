package server.SpotifyMovies.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "list_details")
public class ListDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listDetailsId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate lastTimeEdit;

    @Column
    private String name;

    @Column
    private boolean privateList;

    public ListDetails(){}

    public Long getListDetailsId() {
        return listDetailsId;
    }

    public void setListDetailsId(Long listDetailsId) {
        this.listDetailsId = listDetailsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivateList() {
        return privateList;
    }

    public void setPrivateList(boolean privateList) {
        this.privateList = privateList;
    }
}
