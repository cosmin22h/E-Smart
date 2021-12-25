package ro.tuc.ds2021.handaric.cosmin.backend.repositories.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Sensor;

import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, UUID> {
}
