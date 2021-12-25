package ro.tuc.ds2021.handaric.cosmin.backend.entities.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Sensor;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "device")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Lob
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "max_energy_consumption")
    private double maxEnergyConsumption;

    @Column(name = "average_energy_consumption")
    private double averageEnergyConsumption;

    @Column(name = "disabled")
    private boolean disabled;

    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    @OneToOne(targetEntity = Sensor.class, cascade = CascadeType.ALL)
    private Sensor sensor;
}
