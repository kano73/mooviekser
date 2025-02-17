package org.movier.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonProperty("id")
    private Long id;

    @JsonProperty("poster_path")
    @Column(name="icon_url")
    private String posterPath;

    @JsonProperty("title")
    @Column(name="title", nullable = false)
    private String title;

    @JsonProperty("vote_average")
    @Column(name="vote_average")
    private Float voteAverage;

    @JsonProperty("vote_count")
    @Column(name="vote_count")
    private Integer voteCount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("release_date")
    @Column(name="release_date")
    private LocalDate releaseDate;

    @Column(name="overview",columnDefinition = "TEXT")
    private String overview;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
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
        return Objects.equals(id, myMovie.id) && Objects.equals(title, myMovie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "MyMovie{" +
                "id=" + id +
                ", poster_path='" + posterPath + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + voteAverage +
                ", vote_count=" + voteCount +
                ", release_date=" + releaseDate +
                ", overview='" + overview + '\'' +
                ", genres=" + genres +
                '}';
    }
}
