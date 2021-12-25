package ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SensorDTO {
    private UUID id;
    private String description;
    private Double maxValue;
    private List<RecordDTO> records;
}
