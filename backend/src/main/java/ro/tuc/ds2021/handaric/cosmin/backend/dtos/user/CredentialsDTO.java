package ro.tuc.ds2021.handaric.cosmin.backend.dtos.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CredentialsDTO {
    private String username;
    private String password;
}
