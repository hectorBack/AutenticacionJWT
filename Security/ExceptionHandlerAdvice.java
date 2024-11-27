package com.example.BorrarLoginJWT.Security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> elementoNoEncontrado(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no registrado en la base de datos");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("errors", errors);
        mav.setViewName("methodArgumentNotValid");
        return mav;
    }


}
