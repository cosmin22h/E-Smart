package ro.tuc.ds2021.handaric.cosmin.backend.repositories.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.LogMessage;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogMessageRepository extends JpaRepository<LogMessage, UUID> {

    List<LogMessage> getAllByClient(Client client);

    @Query(nativeQuery = true, value = "SELECT * " +
                            "FROM log_message AS lm " +
                            "WHERE lm.client = :client " +
                            "ORDER BY lm.timestamp DESC " +
                            "LIMIT :limit")
    List<LogMessage> getAllByClientForFirstPage(@Param("client") Client client, @Param("limit") int limit);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM log_message AS lm " +
            "WHERE lm.client = :client " +
            "ORDER BY lm.timestamp DESC " +
            "LIMIT :limit " +
            "OFFSET :offset")
    List<LogMessage> getAllByClientForPage(@Param("client") Client client, @Param("offset") int offset, @Param("limit") int limit);

    @Query(nativeQuery = true, value = "SELECT * " +
                            "FROM log_message AS lm " +
                            "WHERE lm.is_read = false " +
                            "AND lm.client = :client " +
                            "ORDER BY lm.timestamp DESC")
    List<LogMessage> getAllUnreadByClient(@Param("client") Client client);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM log_message AS lm " +
            "WHERE lm.is_read = false " +
            "AND lm.client = :client " +
            "ORDER BY lm.timestamp DESC " +
            "LIMIT :limit")
    List<LogMessage> getAllUnreadByClientForFirstPage(@Param("client") Client client, @Param("limit") int limit);

    @Query(nativeQuery = true, value = "SELECT * " +
                            "FROM log_message AS lm " +
                            "WHERE lm.is_read = false " +
                            "AND lm.client = :client " +
                            "ORDER BY lm.timestamp DESC " +
                            "LIMIT :limit " +
                            "OFFSET :offset")
    List<LogMessage> getAllUnreadByClientForPage(@Param("client") Client client, @Param("offset") int offset, @Param("limit") int limit);
}
