package org.movier.model.entity;

import jakarta.persistence.*;

import java.time.Year;
import java.util.Objects;

@Entity
@Table(name="my_movie")
public class MyMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="icon_url")
    private String icon_url;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="rating")
    private Float rating;

    @Column(name="watchedTimes", nullable = false)
    private Long watchedTimes;

    @Column(name="year")
    private Year year;

    @Column(name="duration")
    private Float duration;

    @Column(name="description")
    private String description;

    @Column(name="category", nullable = false)
    private String category;

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Long getWatchedTimes() {
        return watchedTimes;
    }

    public void setWatchedTimes(Long watchedTimes) {
        this.watchedTimes = watchedTimes;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyMovie myMovie = (MyMovie) o;
        return id == myMovie.id && Objects.equals(icon_url, myMovie.icon_url) && Objects.equals(title, myMovie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, icon_url, title);
    }
}
