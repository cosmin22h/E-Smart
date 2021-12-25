package ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor;

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
public class RecordReportDTO {
    private UUID id;
    @JsonFormat(pattern="HH:mm:ss")
    private Date timestamp;
    private Double energyConsumption;
    private String deviceDescription;
}
