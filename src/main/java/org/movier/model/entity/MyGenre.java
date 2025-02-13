package org.movier.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "my_genre")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyGenre {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="movie_id")
    @ManyToMany(mappedBy = "genres")
    private Set<MyMovie> movies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MyMovie> getMovies() {
        return movies;
    }

    public void setMovies(Set<MyMovie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyGenre myGenre = (MyGenre) o;
        return Objects.equals(id, myGenre.id) && Objects.equals(name, myGenre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "MyGenre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", movies=" + movies +
                '}';
    }
}
