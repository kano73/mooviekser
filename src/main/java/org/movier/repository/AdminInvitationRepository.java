package org.movier.repository;

import org.movier.model.entity.AdminInvitation;
import org.movier.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdminInvitationRepository extends JpaRepository<AdminInvitation, Integer> {

    @Transactional
    void deleteByUser(MyUser user);

    @Transactional
    void deleteByToken(String token);

    Optional<AdminInvitation> findByToken(String token);

    Optional<AdminInvitation> findByUser(MyUser user);
}
