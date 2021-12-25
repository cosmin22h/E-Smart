package ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class NotFoundResourceException extends CustomException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundResourceException(String message, List<String> errors) {
        super(message, HTTP_STATUS, errors);
    }
}
