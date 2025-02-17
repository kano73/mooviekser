package org.movier.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.movier.model.entity.Banned;
import org.movier.model.entity.MyUser;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BannedRepository extends JpaRepository<Banned, Long> {
     Optional<Banned> findByUser(MyUser user);

    @Override
    <S extends Banned> S save(S entity);

    void deleteBannedByUser(MyUser user);
 }
