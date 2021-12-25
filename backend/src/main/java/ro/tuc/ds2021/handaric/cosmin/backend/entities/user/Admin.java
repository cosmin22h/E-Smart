package ro.tuc.ds2021.handaric.cosmin.backend.entities.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Admin extends AppUser {

    public Admin(UUID id, String username, String email, String password, Date joinedDate, List<AuthSession> authSessions) {
        super(id, username, email, password, joinedDate, authSessions);
    }
}
