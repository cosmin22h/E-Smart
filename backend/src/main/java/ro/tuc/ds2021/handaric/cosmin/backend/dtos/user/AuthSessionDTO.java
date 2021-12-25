package ro.tuc.ds2021.handaric.cosmin.backend.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthSessionDTO {
    private UUID id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date logoutTime;
}
