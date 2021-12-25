package ro.tuc.ds2021.handaric.cosmin.backend.entities.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.device.Device;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.LogMessage;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client extends AppUser {

    @Column(name = "firstname")
    private String firsName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "disabled")
    private boolean disabled;

    @Column(name = "birthday")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(targetEntity = Device.class, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Device> devices = new ArrayList<>();

    @OneToMany(targetEntity = LogMessage.class, mappedBy = "client", cascade = CascadeType.ALL)
    private List<LogMessage> logMessages = new ArrayList<>();

    public Client(UUID id, String username, String email, String password, Date joinedDate, List<AuthSession> authSessions,
                   String firsName, String lastName, boolean disabled, LocalDate birthday, Address address) {
        super(id, username, email, password, joinedDate, authSessions);
        this.firsName = firsName;
        this.lastName = lastName;
        this.disabled = disabled;
        this.birthday = birthday;
        this.address = address;
    }

}
