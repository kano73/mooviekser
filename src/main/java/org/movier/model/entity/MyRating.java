package org.movier.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="my_rating")
public class MyRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="value", nullable = false)
    private float value;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser author;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MyMovie movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public MyMovie getMovie() {
        return movie;
    }

    public void setMovie(MyMovie movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyRating myRating = (MyRating) o;
        return id == myRating.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
