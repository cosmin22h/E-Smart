package ro.tuc.ds2021.handaric.cosmin.backend.dtos.rpc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BestEstimatedConsumption {
    private List<Double> chart;
    private Integer startHour;
    private Double currentConsumption;
    private Double estimatedConsumption;
}
