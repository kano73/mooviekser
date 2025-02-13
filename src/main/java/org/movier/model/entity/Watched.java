package org.movier.model.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "watched",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "movie_id"})
)
public class Watched {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MyMovie movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyMovie getMovie() {
        return movie;
    }

    public void setMovie(MyMovie movie) {
        this.movie = movie;
    }
}
