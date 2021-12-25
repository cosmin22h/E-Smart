package ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.device.Device;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "sensor")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Lob
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "max_value")
    private double maxValue;

    @OneToOne(targetEntity = Device.class, mappedBy = "sensor", fetch = FetchType.LAZY)
    private Device device;

    @OneToMany(targetEntity = Record.class, mappedBy = "sensor", cascade = CascadeType.REMOVE)
    private List<Record> records = new ArrayList<>();

}
