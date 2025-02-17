package org.movier.repository;

import org.movier.model.dto.MyMovieVoteDTO;
import org.movier.model.entity.MyMovie;
import org.movier.model.responce.MyMovieSimpleInfoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MyMovieRepository extends JpaRepository<MyMovie, Long> , JpaSpecificationExecutor<MyMovie> {

    @Transactional
    @Override
    <S extends MyMovie> List<S> saveAll(Iterable<S> entities);

    @Transactional
    @Modifying
    @Query(value="UPDATE MyMovie m SET m.voteCount = :voteCount," +
            " m.voteAverage = :voteAverage WHERE m.id = :id ")
    Integer updateVoteById(@Param("voteCount") Integer voteCount, @Param("voteAverage") Float voteAverage, @Param("id") Long id);

    @Query(value = "SELECT 1 FROM my_movie LIMIT 1", nativeQuery = true)
    Integer existsAnyMovie();

    @Query(value = "SELECT release_date FROM my_movie ORDER BY release_date DESC LIMIT 1", nativeQuery = true)
    LocalDate getLatestDate();

    @Query(value = "SELECT m.id, m.voteCount, m.voteAverage FROM MyMovie m WHERE m.id = :id")
    Optional<MyMovieVoteDTO> findByIdVoteAndId(@Param("id") Long id);

    @Query("SELECT m FROM MyMovie m JOIN FETCH m.genres WHERE m.id = :id")
    Optional<MyMovie> findByIdWithGenres(@Param("id") Long id);

    @Query("SELECT new org.movier.model.responce.MyMovieSimpleInfoDTO" +
            "(m.id, m.posterPath, m.title, m.voteAverage, m.releaseDate, m.voteCount)" +
            " FROM MyMovie m")
    List<MyMovieSimpleInfoDTO> findAllSimpleInfo(Pageable pageable);
}
