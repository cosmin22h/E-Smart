package ro.tuc.ds2021.handaric.cosmin.backend.dtos.user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class LoginTokenDTO {
    private String role;
    private UUID id;
    private UUID idSession;
}
