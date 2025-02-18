package org.movier.repository;

import org.movier.model.entity.Banned;
import org.movier.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannedRepository extends JpaRepository<Banned, Long> {
     Optional<Banned> findByUser(MyUser user);

 }
