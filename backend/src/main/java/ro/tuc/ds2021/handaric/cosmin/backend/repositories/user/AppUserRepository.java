package ro.tuc.ds2021.handaric.cosmin.backend.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.AppUser;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findAppUserByUsername(String username);
    AppUser findAppUserByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM app_user " +
            "LIMIT :limit")
    List<AppUser> findAllForFirstPage(@Param("limit") int limit);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM app_user " +
            "OFFSET :offset " +
            "LIMIT :limit")
    List<AppUser> findAllForPage(@Param("offset") int offset, @Param("limit") int limit);
}
