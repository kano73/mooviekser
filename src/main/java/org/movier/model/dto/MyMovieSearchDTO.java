package org.movier.model.dto;

import java.time.LocalDate;
import java.util.List;

public class MyMovieSearchDTO {
    private String title;
    private LocalDate from;
    private LocalDate to;
    private List<Long> genres;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public List<Long> getGenres() {
        return genres;
    }

    public void setGenres(List<Long> genres) {
        this.genres = genres;
    }


    @Override
    public String toString() {
        return "MyMovieSearchDTO{" +
                "title='" + title + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", genres=" + genres +
                '}';
    }
}
