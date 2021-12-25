package ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(name = "log_message")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LogMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "timestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    @OneToOne(targetEntity = Record.class, mappedBy = "logMessage", fetch = FetchType.EAGER)
    private Record record;

    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;
}
