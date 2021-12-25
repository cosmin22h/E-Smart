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
import java.util.*;

@Entity(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "joined_date", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date joinedDate;

    @OneToMany(targetEntity = AuthSession.class, mappedBy = "user", cascade = CascadeType.ALL)
    private List<AuthSession> authSessions = new ArrayList<>();
}
