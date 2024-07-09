package it.uniroma3.siw.siwpers.exceptions;

import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException {

    public final HttpStatus httpStatus;

    public AuthException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
