package org.movier.repository;

import org.movier.model.entity.MyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends CrudRepository<MyUser, Long> {
    Optional<MyUser> findByUsernameIgnoreCase(String username);

    Optional<MyUser> findByEmailIgnoreCase(String email);

    <S extends MyUser> S save(S entity);
}
