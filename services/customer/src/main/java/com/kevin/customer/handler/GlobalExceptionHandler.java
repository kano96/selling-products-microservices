package com.kevin.customer.handler;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.kevin.customer.exception.CostumerNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CostumerNotFoundException.class)
  public ResponseEntity<String> handleCostumerNotFoundException(CostumerNotFoundException e) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(e.getMsg());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleCostumerNotFoundException(MethodArgumentNotValidException e) {
    var errors = new HashMap<String,String>();
    e.getBindingResult().getAllErrors().forEach((error) -> {
      var fieldName = ((FieldError) error).getField();
      var errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(errors));
  }
}
