package ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final List<String> errors;

    public CustomException(String message, HttpStatus httpStatus, List<String> errors){
        super(message);
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

}