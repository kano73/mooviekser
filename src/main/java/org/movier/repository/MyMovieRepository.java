package org.movier.repository;

import org.movier.model.entity.MyMovie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyMovieRepository extends CrudRepository<MyMovie, Integer> {
}
