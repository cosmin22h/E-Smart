package ro.tuc.ds2021.handaric.cosmin.backend.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDTO extends UserDTO {
    private String firstName;
    private String lastName;
    private boolean disabled;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;
    private AddressDTO address;
    private int noOfDevices;
}
