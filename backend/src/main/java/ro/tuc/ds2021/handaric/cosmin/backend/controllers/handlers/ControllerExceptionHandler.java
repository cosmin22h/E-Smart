package ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.ApiExceptionResponseDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.CustomException;


@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<Object> handleApiExceptionResponse(CustomException ex, WebRequest request){
        HttpStatus httpStatus = ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        CustomException customException = new CustomException(ex.getMessage(), httpStatus, ex.getErrors());
        ApiExceptionResponseDTO errorInformation = responseEntityBuilder(customException, request);
        return handleExceptionInternal(
                customException,
                errorInformation,
                new HttpHeaders(),
                customException.getHttpStatus(),
                request
        );
    }

    private ApiExceptionResponseDTO responseEntityBuilder(CustomException ex, WebRequest request){
        return new ApiExceptionResponseDTO(
                ex.getHttpStatus().value(),
                ex.getMessage(),
                request.getDescription(false),
                ex.getErrors()
                );
    }

}
