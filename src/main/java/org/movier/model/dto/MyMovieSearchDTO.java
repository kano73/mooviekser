package org.movier.model.dto;

import org.movier.model.entity.MyGenre;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Set;

public class MyMovieSearchDTO {
    private String title;
    private LocalDate from;
    private LocalDate to;
    private Set<MyGenre> genres;
    private Pageable pageable;

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

    public Set<MyGenre> getGenres() {
        return genres;
    }

    public void setGenres(Set<MyGenre> genres) {
        this.genres = genres;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
