package ro.tuc.ds2021.handaric.cosmin.backend.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChangePasswordDTO {
    private Boolean isAdmin;
    private UUID idUserToChange;
    private String oldPassword;
    private String newPassword;
}
