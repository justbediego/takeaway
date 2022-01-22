package com.takeaway.takeaway.configuration;

import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.business.exception.UnrecognizedException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Builder
@Data
class ExceptionResponse {
    private String type;
    private String message;
    private String details;
}

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = TakeawayException.class)
    protected ResponseEntity<ExceptionResponse> handleConflict(TakeawayException ex, WebRequest request) {
        ex.printStackTrace();
        ExceptionResponse responseBody = ExceptionResponse.builder()
                .type(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .details(ex.getDetails())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(responseBody);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        ex.printStackTrace();
        UnrecognizedException newException = new UnrecognizedException(ex.getMessage());
        ExceptionResponse responseBody = ExceptionResponse.builder()
                .type(newException.getClass().getSimpleName())
                .message(newException.getMessage())
                .details(newException.getDetails())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(responseBody);
    }

}
