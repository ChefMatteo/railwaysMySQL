package it.uniprisma.railwaysMySQL.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
        log.error(message);
    }
}
