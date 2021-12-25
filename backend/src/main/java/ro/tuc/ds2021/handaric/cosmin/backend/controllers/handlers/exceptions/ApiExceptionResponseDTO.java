package ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ApiExceptionResponseDTO {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private Collection<?> details;

    public ApiExceptionResponseDTO(int status, String message, String path, Collection<?> details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
        this.details = details;
    }
}
