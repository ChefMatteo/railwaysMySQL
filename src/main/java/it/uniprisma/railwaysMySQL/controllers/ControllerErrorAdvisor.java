package it.uniprisma.railwaysMySQL.controllers;

import it.uniprisma.railwaysMySQL.utils.BadRequestException;
import it.uniprisma.railwaysMySQL.utils.ConflictException;
import it.uniprisma.railwaysMySQL.utils.ErrorResponse;
import it.uniprisma.railwaysMySQL.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerErrorAdvisor {

    @ExceptionHandler({BadRequestException.class, ConflictException.class, NotFoundException.class})
    protected ResponseEntity<Object> handleCustomException(Exception ex){
        return ResponseEntity
                .status(ex.getClass().getAnnotation(ResponseStatus.class).value())
                .body(ErrorResponse.builder()
                        .exceptionName(ex.getClass().getName())
                        .message(ex.getMessage())
                        .position(ex.getStackTrace()[0].toString()));
    }


}
