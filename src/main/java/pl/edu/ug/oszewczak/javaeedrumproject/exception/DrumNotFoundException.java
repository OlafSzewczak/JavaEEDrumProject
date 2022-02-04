package pl.edu.ug.oszewczak.javaeedrumproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DrumNotFoundException extends RuntimeException {

    public DrumNotFoundException(Long id) {
        super("Could not find drum with id " + id);
    }

}
