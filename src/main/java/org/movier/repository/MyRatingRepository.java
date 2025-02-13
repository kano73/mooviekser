package org.movier.repository;

import org.movier.model.entity.MyRating;
import org.springframework.data.repository.CrudRepository;

public interface MyRatingRepository extends CrudRepository<MyRating, Integer> {
    @Override
    <S extends MyRating> S save(S entity);
}
