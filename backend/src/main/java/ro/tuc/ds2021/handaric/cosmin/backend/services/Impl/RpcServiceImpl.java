package ro.tuc.ds2021.handaric.cosmin.backend.services.Impl;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.NotFoundResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.rpc.BestEstimatedConsumption;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.device.Device;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Record;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.device.DeviceRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.ClientRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.services.RpcService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AutoJsonRpcServiceImpl
public class RpcServiceImpl implements RpcService {

    private final ClientRepository clientRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public RpcServiceImpl(ClientRepository clientRepository, DeviceRepository deviceRepository) {
        this.clientRepository = clientRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    @Transactional
    public List<HashMap<LocalDate, Double>> getHistoricalConsumption(UUID idClient, int d) {
        Client client = this.clientRepository.findById(idClient).orElseThrow(this::clientNotFound);
        List<HashMap<LocalDate, Double>> totalConsumptions = new ArrayList<>();
        List<LocalDate> lastDays = this.getLastDays(d);
        for (int i = 0; i < 24; i++) {
            HashMap<LocalDate, Double> dayValues = new HashMap<>();
            for (LocalDate currentDay : lastDays) {
                double totalConsumption = this.computeTotalConsumption(client, currentDay, i);
                dayValues.put(currentDay, totalConsumption);
            }
            totalConsumptions.add(dayValues);
        }

        Double prevConsumption = getLastValueFromPrevDays(client, d);
        return this.computeHourConsumption(totalConsumptions, lastDays.get(0), prevConsumption);
    }

    @Override
    @Transactional
    public List<Double> getBaseline(UUID idClient) {
        List<Double> baseline = new ArrayList<>();
        List<HashMap<LocalDate, Double>> lastWeekConsumption = this.getHistoricalConsumption(idClient, 7);
        for (HashMap<LocalDate, Double> hourConsumptionForWeek: lastWeekConsumption) {
            double hourBaseline = 0.0f;
            for (Map.Entry entry: hourConsumptionForWeek.entrySet()) {
                hourBaseline += (Double) entry.getValue();
            }
            hourBaseline /= 7.0f;
            baseline.add(hourBaseline);
        }

        return baseline;
    }

    @Override
    @Transactional
    public BestEstimatedConsumption getBestProgram(UUID idClient, UUID idDevice, Integer program) {
        List<Double> baseline = this.getBaseline(idClient);
        Device device = this.deviceRepository.findById(idDevice).orElseThrow(this::deviceNotFound);

        double maxValueDevice = device.getSensor().getMaxValue();
        List<HashMap<Integer, Double>> starts = this.addMaxValueToBaseline(baseline, maxValueDevice, program);
        HashMap<Integer, Double> bestStart = this.getBestProgram(starts);
        List<Double> estimatedConsumptionGraph = this.getEstimatedConsumptionGraph(baseline, bestStart);

        return BestEstimatedConsumption.builder()
                .chart(estimatedConsumptionGraph)
                .startHour(bestStart.entrySet().iterator().next().getKey())
                .currentConsumption(this.getCurrentConsumptionValue(baseline))
                .estimatedConsumption(this.getEstimatedConsumptionValue(estimatedConsumptionGraph))
                .build();
    }

    private Double computeTotalConsumption(Client client, LocalDate currentDay, int hour) {
        double totalConsumption = 0.0f;
        for (Device device : client.getDevices()) {
            Record lastRecord = null;
            List<Record> records = device.getSensor().getRecords();
            Collections.reverse(records);
            for (Record record : records) {
                LocalDate timestampLocal = new java.sql.Date(record.getTimestamp().getTime()).toLocalDate();
                if (!timestampLocal.equals(currentDay)) {
                    continue;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(record.getTimestamp());
                if (calendar.get(Calendar.HOUR_OF_DAY) == hour) {
                    lastRecord = record;
                }
            }
            if (lastRecord != null) {
                totalConsumption += lastRecord.getEnergyConsumption();
            }
        }
        return totalConsumption;
    }

    private List<HashMap<LocalDate, Double>> computeHourConsumption(List<HashMap<LocalDate, Double>> totalConsumptions,
                                                                    LocalDate firstDate,
                                                                    Double prevConsumption) {
        List<HashMap<LocalDate, Double>> historicalConsumption = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            HashMap<LocalDate, Double> deltaConsumption = new HashMap<>();
            HashMap<LocalDate, Double> currentHour = totalConsumptions.get(i);

            for (Map.Entry entry: currentHour.entrySet()) {
                double difference;
                if (i == 0) {
                    if (entry.getKey().equals(firstDate)) {
                        difference = (Double) entry.getValue() - prevConsumption;
                    } else {
                        HashMap<LocalDate, Double> prevDayLastHour = totalConsumptions.get(totalConsumptions.size() - 1);
                        difference =(Double) entry.getValue() - prevDayLastHour.get(((LocalDate) entry.getKey()).minusDays(1));
                    }
                } else {
                    HashMap<LocalDate, Double> lastHour = totalConsumptions.get(i - 1);
                    difference = (Double) entry.getValue() - lastHour.get(entry.getKey());
                }
                if (difference < 0) {
                    difference = 0;
                }
                deltaConsumption.put((LocalDate) entry.getKey(), difference);
            }
            historicalConsumption.add(deltaConsumption);
        }

        return historicalConsumption;
    }

    private List<LocalDate> getLastDays(int d) {
        LocalDate daysBeforeToday = LocalDate.now().minusDays(d);
        return IntStream.range(1, d + 1)
                .mapToObj(daysBeforeToday::plusDays)
                .collect(Collectors.toList());
    }

    private Double getLastValueFromPrevDays(Client client, int d) {
        List<LocalDate> days = this.getLastDays(d + 1);
        LocalDate day = days.get(0);
        return this.computeTotalConsumption(client, day, 23);
    }

    private List<HashMap<Integer, Double>> addMaxValueToBaseline(List<Double> baseline,
                                                                 double maxValueDevice,
                                                                 int program) {
        List<HashMap<Integer, Double>> starts = new ArrayList<>();
        for (int i = 0; i < 25 - program; i++) {
            HashMap<Integer, Double> currentStart = new HashMap<>();
            for (int j = i; j < i + program; j++) {
                currentStart.put(j, (baseline.get(j) * 7.0 + maxValueDevice) / 7);
            }
            starts.add(currentStart);
        }

        return starts;
    }

    private HashMap<Integer, Double> getBestProgram(List<HashMap<Integer, Double>> starts) {
        HashMap<Integer, Double> bestStart = starts.get(0);
        double minConsumption = Double.MAX_VALUE;
        for (HashMap<Integer, Double> currentStart: starts) {
            double currentConsumption = 0.0f;
            for (Map.Entry entry: currentStart.entrySet()) {
                currentConsumption += (Double) entry.getValue();
            }
            if (currentConsumption < minConsumption) {
                minConsumption = currentConsumption;
                bestStart = currentStart;
            }
        }

        return bestStart;
    }

    private List<Double> getEstimatedConsumptionGraph(List<Double> baseline, HashMap<Integer, Double> bestStart) {
        HashMap<Integer, Double> estimatedConsumption = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            estimatedConsumption.put(i, baseline.get(i));
        }
        for (Map.Entry entry: bestStart.entrySet()) {
            estimatedConsumption.put((Integer) entry.getKey(), (Double) entry.getValue());
        }
        List<Double> estimatedConsumptionArray = new ArrayList<>();
        for (Map.Entry entry: estimatedConsumption.entrySet()) {
            estimatedConsumptionArray.add((Double) entry.getValue());
        }

        return estimatedConsumptionArray;
    }

    private double getCurrentConsumptionValue(List<Double> baseline) {
        double currentConsumptionValue = 0.0f;
        for (Double value: baseline) {
            currentConsumptionValue += value;
        }
        currentConsumptionValue /= 24.0f;

        return currentConsumptionValue;
    }

    private double getEstimatedConsumptionValue(List<Double> estimatedConsumptionGraph) {
        double estimatedConsumptionValue = 0.0f;
        for (Double value: estimatedConsumptionGraph) {
            estimatedConsumptionValue += value;
        }
        estimatedConsumptionValue /= 24.0f;

        return estimatedConsumptionValue;
    }

    private NotFoundResourceException clientNotFound() {
        String message = "Client not found!";
        List<String> errors = new ArrayList<>();
        errors.add("error.rpc.client-not-found");

        return new NotFoundResourceException(message, errors);
    }

    private NotFoundResourceException deviceNotFound() {
        String message = "Device not found!";
        List<String> errors = new ArrayList<>();
        errors.add("error.rpc.device-not-found");

        return new NotFoundResourceException(message, errors);
    }
}
