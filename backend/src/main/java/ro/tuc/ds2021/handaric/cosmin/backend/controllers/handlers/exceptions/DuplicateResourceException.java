package ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class DuplicateResourceException extends CustomException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public DuplicateResourceException(String message, List<String> errors) {
        super(message, HTTP_STATUS, errors);
    }
}
