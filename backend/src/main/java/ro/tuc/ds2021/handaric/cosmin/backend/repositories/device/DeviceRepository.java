package ro.tuc.ds2021.handaric.cosmin.backend.repositories.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.device.Device;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM device " +
            "LIMIT :limit")
    List<Device> findForFirstPage(@Param("limit") int limit);

    @Query(nativeQuery = true, value = "SELECT *" +
            "FROM device " +
            "OFFSET :offset " +
            "LIMIT :limit")
    List<Device> findForPage(@Param("offset") int offset, @Param("limit") int limit);

    List<Device> findAllByClient(Client client);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM device as dev " +
            "WHERE dev.client = :client " +
            "LIMIT :limit")
    List<Device> findAllForFirstPageByClient(@Param("client") Client client, @Param("limit") int limit);

    @Query(nativeQuery = true, value = "SELECT *" +
            "FROM device as dev " +
            "WHERE dev.client = :client " +
            "OFFSET :offset " +
            "LIMIT :limit")
    List<Device> findAllForPageByClient(@Param("client") Client client, @Param("offset") int offset, @Param("limit") int limit);
}
