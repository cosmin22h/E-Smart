package ro.tuc.ds2021.handaric.cosmin.backend.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LogoutDTO {
    private String message;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date logoutTime;
}
