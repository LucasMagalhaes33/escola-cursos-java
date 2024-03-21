 package com.example.escolacursosjava.controller;

 import com.example.escolacursosjava.exception.RecordNotFoundException;
 import jakarta.validation.ConstraintViolationException;
 import org.springframework.http.HttpStatus;
 import org.springframework.web.bind.MethodArgumentNotValidException;
 import org.springframework.web.bind.annotation.ExceptionHandler;
 import org.springframework.web.bind.annotation.ResponseStatus;
 import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RecordNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .reduce("", (acc, error) -> acc + error + "\n" );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(MethodArgumentNotValidException ex) {
       if (ex != null) {
           return ex.getBindingResult().getFieldErrors().stream()
                   .map(error -> error.getField() + " " + error.getDefaultMessage())
                   .reduce("", (acc, error) -> acc + error + "\n" );
       }

       return "not a valid request, please check the request and try again";
    }

}
