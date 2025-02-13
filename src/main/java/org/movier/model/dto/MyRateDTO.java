package org.movier.model.dto;

import jakarta.validation.constraints.NotNull;

public class MyRateDTO {
    @NotNull
    private Long movieId;

    @NotNull
    private Float rate;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
