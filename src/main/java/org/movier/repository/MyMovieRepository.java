package org.movier.repository;

import org.movier.model.entity.MyMovie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface MyMovieRepository extends CrudRepository<MyMovie, Long> {

    @Transactional
    @Override
    <S extends MyMovie> Iterable<S> saveAll(Iterable<S> entities);

    @Query(value = "SELECT 1 FROM my_movie LIMIT 1", nativeQuery = true)
    Integer existsAnyMovie();

    @Query(value = "SELECT release_date FROM my_movie ORDER BY release_date DESC LIMIT 1", nativeQuery = true)
    LocalDate getLatestDate();
}
