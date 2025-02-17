package org.movier.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MyRatingDTO {
    @NotNull
    private Long movieId;

    @DecimalMin(value = "0.1", message = "Rating must be more than 0.1")
    @DecimalMax(value = "10.0", message = "Rating must be less than 10.0")
    @NotNull
    private Float rating;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
