package org.movier.model.responce;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class MyMovieSimpleInfoDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("title")
    private String title;

    @JsonProperty("vote_average")
    private Float voteAverage;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @JsonProperty("vote_count")
    private Integer voteCount;

    public MyMovieSimpleInfoDTO() {
    }

    public MyMovieSimpleInfoDTO(Long id, String posterPath, String title, Float voteAverage, LocalDate releaseDate, Integer voteCount) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
