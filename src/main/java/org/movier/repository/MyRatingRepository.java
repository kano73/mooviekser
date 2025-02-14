package org.movier.repository;

import org.movier.model.entity.MyRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyRatingRepository extends CrudRepository<MyRating, Integer> {
    @Override
    <S extends MyRating> S save(S entity);

    @Query("SELECT r.value FROM MyRating r WHERE r.movie.id = :movieId")
    List<Float> findAllRatingsByMovieId(@Param("id") Long movieId);
}
