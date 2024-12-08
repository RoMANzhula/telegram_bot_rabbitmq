package org.romanzhula.rest_dispatcher.exception_handlers;

import org.romanzhula.rest_dispatcher.exceptions.InternalServerException;
import org.romanzhula.rest_dispatcher.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> handleInternalServerError(InternalServerException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

}
