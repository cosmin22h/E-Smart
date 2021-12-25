package ro.tuc.ds2021.handaric.cosmin.backend.entities.user;

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

@Entity(name = "auth_session")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "login_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    @Column(name = "logout_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date logoutTime;

    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

}
