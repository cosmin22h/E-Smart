package ro.tuc.ds2021.handaric.cosmin.backend.services;

import com.googlecode.jsonrpc4j.*;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.rpc.BestEstimatedConsumption;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@JsonRpcService("/api/rpc")
public interface RpcService {

    @JsonRpcMethod("getHistoricalConsumption")
    List<HashMap<LocalDate, Double>> getHistoricalConsumption(@JsonRpcParam(value = "idClient") UUID idClient,
                                                              @JsonRpcParam(value = "d") int d);

    @JsonRpcMethod("getBaseline")
    List<Double> getBaseline(@JsonRpcParam(value = "idClient") UUID idClient);

    @JsonRpcMethod("getBestProgram")
    BestEstimatedConsumption getBestProgram(@JsonRpcParam(value = "idClient") UUID idClient,
                                            @JsonRpcParam(value = "idDevice") UUID idDevice,
                                            @JsonRpcParam(value = "program") Integer program);
}
