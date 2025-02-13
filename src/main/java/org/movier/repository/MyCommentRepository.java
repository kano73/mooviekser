package org.movier.repository;

import org.movier.model.entity.MyComment;
import org.movier.model.entity.MyMovie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyCommentRepository extends CrudRepository<MyComment, Long> {

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends MyComment> S save(S entity);

    List<MyComment> findAllByMovie(MyMovie movie);
}
