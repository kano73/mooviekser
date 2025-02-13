package org.movier.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="my_movie")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyMovie {
    @Id
    private Long id;

    @Column(name="icon_url")
    private String poster_path;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="vote_average")
    private Float vote_average;

    @Column(name="vote_count")
    private Long vote_count;

    @Column(name="release_date")
    private LocalDate release_date;

    @Column(name="overview",columnDefinition = "TEXT")
    private String overview;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_genre"))
    private Set<MyGenre> genres;

    @JsonProperty("genre_ids")
    public void getGenresFromIds(List<Long> genreIds){
        this.genres = genreIds.stream().map(num->{
            MyGenre genre = new MyGenre();
            genre.setId(num);
            return genre;
        }).collect(Collectors.toSet());
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }

    public Long getVote_count() {
        return vote_count;
    }

    public void setVote_count(Long vote_count) {
        this.vote_count = vote_count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Set<MyGenre> getGenres() {
        return genres;
    }

    public void setGenres(Set<MyGenre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyMovie myMovie = (MyMovie) o;
        return id == myMovie.id && Objects.equals(title, myMovie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "MyMovie{" +
                "id=" + id +
                ", poster_path='" + poster_path + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", release_date=" + release_date +
                ", overview='" + overview + '\'' +
                ", genres=" + genres +
                '}';
    }
}
