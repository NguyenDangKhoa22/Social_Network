package com.example.backend.exeption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHandle {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handlingRunTimeExeption(RuntimeException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlingValidateion(MethodArgumentNotValidException manve){
        return ResponseEntity.badRequest().body(manve.getFieldError().getDefaultMessage());
    }
}
