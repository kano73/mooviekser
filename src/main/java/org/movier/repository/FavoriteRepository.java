package org.movier.repository;

import org.movier.model.entity.Favorite;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoriteRepository extends CrudRepository<Favorite, Integer> {
    @Override
    <S extends Favorite> S save(S entity);

    List<Favorite> findAllByUser(MyUser user, Pageable pageable);

    void deleteByUserAndMovie(MyUser user, MyMovie movie);

}
