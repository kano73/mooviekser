package org.movier.repository;

import org.movier.model.entity.EmailValidation;
import org.movier.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmailValidationRepository extends JpaRepository<EmailValidation, Integer> {

    <S extends EmailValidation> S save(S entity);

    List<EmailValidation> findByToken(String token);

    List<EmailValidation> findByUser(MyUser user);

    @Modifying
    @Transactional
    @Query("DELETE FROM EmailValidation ev WHERE ev.user = :user")
    void deleteByUser(@Param("user") MyUser user);
}