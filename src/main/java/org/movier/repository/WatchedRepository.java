package org.movier.repository;

import org.movier.model.entity.MyUser;
import org.movier.model.entity.Watched;
import org.movier.model.responce.MyMovieSimpleInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface WatchedRepository extends JpaRepository<Watched, Integer> {

        
    @Query("SELECT new org.movier.model.responce.MyMovieSimpleInfoDTO( " +
            "m.id, m.posterPath, m.title, m.voteAverage, m.releaseDate, m.voteCount) " +
            "FROM Watched w JOIN w.movie m WHERE w.user = :user")
    List<MyMovieSimpleInfoDTO> findAllMoviesByUser(@Param("user") MyUser user);

    Optional<Watched> findByUserAndMovie_Id(MyUser user, Long movieId);

    @Transactional
    void deleteByUserAndMovie_Id(MyUser user, Long movieId);
}
