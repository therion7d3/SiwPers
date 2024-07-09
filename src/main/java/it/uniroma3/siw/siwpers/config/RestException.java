package it.uniroma3.siw.siwpers.config;

import it.uniroma3.siw.siwpers.exceptions.AuthException;
import it.uniroma3.siw.siwpers.exceptions.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestException {
    @ExceptionHandler(value = { AuthException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AuthException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorDto(ex.getMessage()));
    }
}
