package org.movier.repository;

import org.movier.model.entity.MyComment;
import org.movier.model.responce.MyCommentResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyCommentRepository extends CrudRepository<MyComment, Long> {

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends MyComment> S save(S entity);

    @Query("SELECT new  org.movier.model.responce.MyCommentResponse(c.id, c.text, u.username, u.id, c.timestamp) " +
            "FROM MyComment c " +
            "JOIN c.author u " +
            "WHERE c.movie.id = :id")
    List<MyCommentResponse> findAllByMoviePublicInfo(@Param("id") Long id);
}
