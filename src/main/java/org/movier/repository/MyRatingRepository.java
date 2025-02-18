package org.movier.repository;

import org.movier.model.entity.MyRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MyRatingRepository extends CrudRepository<MyRating, Integer> {
    @Query("SELECT r.value FROM MyRating r WHERE r.author.id= :authorId AND r.movie.id = :movieId")
    Optional<Float> findFirstValueByAuthor_IdAndMovie_Id(@Param("authorId") Long authorId, @Param("movieId") Long movieId);

    @Transactional
    void deleteByAuthor_IdAndMovie_Id(Long authorId, Long movieId);
}
