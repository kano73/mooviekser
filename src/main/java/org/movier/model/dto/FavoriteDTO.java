package org.movier.model.dto;

import jakarta.validation.constraints.NotNull;

public class FavoriteDTO {
    @NotNull
    private Long movieId;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
