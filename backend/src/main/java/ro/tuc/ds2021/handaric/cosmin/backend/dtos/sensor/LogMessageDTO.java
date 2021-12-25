package ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceDTO;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LogMessageDTO {
    private UUID id;
    private Boolean isRead;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    private RecordDTO record;
    private DeviceDTO device;
}
