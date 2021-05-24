package it.uniprisma.railwaysMySQL.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Slf4j
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
        log.error(message);
    }
}
