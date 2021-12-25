package ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(name = "record")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "timestamp", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    @Column(name = "energy_consumption", nullable = false)
    private double energyConsumption;

    @ManyToOne(targetEntity = Sensor.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor")
    private Sensor sensor;

    @OneToOne(targetEntity = LogMessage.class, cascade = CascadeType.ALL)
    private LogMessage logMessage;

}
