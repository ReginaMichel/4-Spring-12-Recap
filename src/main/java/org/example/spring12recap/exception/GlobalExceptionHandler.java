package org.example.spring12recap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    //Alternativ GONE, wenn es schon dauerhaft entfernt wurde.
    //Handlet aktuell die Exceptions, wenn zwei verschiedene User gleichzeitig
    //ein Objekt löschen wollen, das UI des anderen aber nicht aktuell ist
    //und handlet auch den Fall, dass Undo versucht wird, aber schon leer ist.
    public String handleNullPointerException(NullPointerException e) {
        return e.getMessage();
    }
}
