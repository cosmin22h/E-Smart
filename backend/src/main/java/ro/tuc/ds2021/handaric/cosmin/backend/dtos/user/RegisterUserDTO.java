package ro.tuc.ds2021.handaric.cosmin.backend.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class RegisterUserDTO {
    private String username;
    private String email;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;
    private AddressDTO address;
}
