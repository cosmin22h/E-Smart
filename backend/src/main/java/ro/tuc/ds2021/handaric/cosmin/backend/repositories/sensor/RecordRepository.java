package ro.tuc.ds2021.handaric.cosmin.backend.repositories.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Record;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Sensor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {

    @Query(nativeQuery = true, value = "SELECT * " +
                                    "FROM record AS rec " +
                                    "WHERE rec.sensor = :sensor " +
                                    "ORDER BY rec.timestamp DESC " +
                                    "OFFSET 1 " +
                                    "LIMIT 1")
    Record findLastRecordForSensorBeforeLastInsert(@Param("sensor") Sensor sensor);

    List<Record> findAllBySensor(Sensor sensor);
}
