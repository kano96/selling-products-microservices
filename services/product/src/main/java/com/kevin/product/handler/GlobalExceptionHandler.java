package com.kevin.product.handler;

import com.kevin.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;


@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ProductPurchaseException.class)
  public ResponseEntity<String> handleCostumerNotFoundException(ProductPurchaseException e) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST  )
        .body(e.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST  )
            .body(e.getMessage());
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
