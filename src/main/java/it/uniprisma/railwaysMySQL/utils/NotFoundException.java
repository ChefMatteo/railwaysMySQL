package it.uniprisma.railwaysMySQL.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String resource, int id) {
        super(String.format(resource +" not found with id: %s", id));
        log.error(String.format(resource +" not found with id: %s", id));
    }
}
