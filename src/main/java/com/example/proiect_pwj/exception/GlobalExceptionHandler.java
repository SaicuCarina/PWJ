package com.example.proiect_pwj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();

        // NOT FOUND (404)
        if (message.contains("Evenimentul nu a fost gasit") ||
                message.contains("Eveniment negasit") ||
                message.contains("Evenimentul nu exista") ||
                message.contains("Email inexistent")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        // UNAUTHORIZED (401)
        if (message.contains("Sesiune expirata") ||
                message.contains("Te rugam sa te reloghezi") ||
                message.contains("Parola incorecta") ||
                message.contains("Trebuie să fii logat")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }

        // BAD REQUEST (400)
        if (message.contains("deja trecut deja") ||
                message.contains("nu a avut loc inca") ||
                message.contains("nu sunt destule locuri") ||
                message.contains("deja inregistrat") ||
                message.contains("Doar persoanele care au rezervat bilete")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}