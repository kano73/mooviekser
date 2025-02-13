package org.movier.repository;

import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.movier.model.entity.Watched;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchedRepository extends JpaRepository<Watched, Integer> {

    @Override
    <S extends Watched> S save(S entity);

    List<Watched> findAllByUser(MyUser user);

    long countFavoriteByUser(MyUser user);

    long countFavoriteByMovie(MyMovie movie);

    void deleteByUserAndMovie(MyUser user, MyMovie movie);
}
