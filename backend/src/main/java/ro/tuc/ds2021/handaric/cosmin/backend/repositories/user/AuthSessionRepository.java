package ro.tuc.ds2021.handaric.cosmin.backend.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.AuthSession;

import java.util.UUID;

@Repository
public interface AuthSessionRepository extends JpaRepository<AuthSession, UUID> {
}
