package ro.tuc.ds2021.handaric.cosmin.backend.utils;

import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordReportDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.AuthSession;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static int computeOffset(int noOfPage, int itemsPerPage) {
        return itemsPerPage * (noOfPage - 1);
    }
    public static AuthSession getLastSession(List<AuthSession> authSessions) {
        List<AuthSession> listSorted = authSessions.stream()
                .sorted(Comparator.comparing(AuthSession::getLoginTime).reversed())
                .collect(Collectors.toList());
         return listSorted.size() != 0 ? listSorted.get(0): null;
    }
    public static List<RecordReportDTO> sortRecords(List<RecordReportDTO> records) {
        return records.stream()
                .sorted(Comparator.comparing(RecordReportDTO::getTimestamp))
                .collect(Collectors.toList());
    }

}
