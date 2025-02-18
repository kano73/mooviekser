package org.movier.repository;

import org.movier.model.entity.MyUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MyUserRepository extends CrudRepository<MyUser, Long> {
    Optional<MyUser> findByUsernameIgnoreCase(String username);

    Optional<MyUser> findByEmailIgnoreCase(String email);

    @Modifying
    @Transactional
    @Query("UPDATE MyUser u SET u.emailActivated = :emailActivated WHERE u.id = :id")
    void updateUserEmailActivatedById(@Param("id") Long id, @Param("emailActivated") Boolean emailActivated);
}
