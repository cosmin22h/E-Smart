package ro.tuc.ds2021.handaric.cosmin.backend.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findClientByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM app_user as u " +
            "WHERE u.dtype = 'Client'" +
            "LIMIT :limit")
    List<Client> findAllForFirstPate(@Param("limit") int limit);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM app_user as u " +
            "WHERE u.dtype = 'Client' " +
            "OFFSET :offset " +
            "LIMIT :limit")
    List<Client> findAllForPage(@Param("offset") int offset, @Param("limit") int limit);
}
