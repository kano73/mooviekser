package org.movier.repository;

import org.movier.model.entity.MyGenre;
import org.movier.model.entity.MyMovie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface MyMovieRepository extends CrudRepository<MyMovie, Long> {

    @Transactional
    @Override
    <S extends MyMovie> Iterable<S> saveAll(Iterable<S> entities);

    @Query(value = "SELECT 1 FROM my_movie LIMIT 1", nativeQuery = true)
    Integer existsAnyMovie();

    @Query(value = "SELECT release_date FROM my_movie ORDER BY release_date DESC LIMIT 1", nativeQuery = true)
    LocalDate getLatestDate();

    List<MyMovie> findAll(Pageable pageable);

    @Query("""
        SELECT DISTINCT m FROM MyMovie m
        LEFT JOIN m.genres g
        WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:from IS NULL OR :to IS NULL OR m.release_date BETWEEN :from AND :to)
        AND (:genres IS NULL OR g IN :genres)
    """)
    List<MyMovie> findAllByFilters(
            @Param("title") String title,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("genres") Set<MyGenre> genres,
            Pageable pageable);



}
