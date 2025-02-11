package org.movier.repository;

import org.movier.model.entity.MyComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyCommentRepository extends CrudRepository<MyComment, Long> {

}
