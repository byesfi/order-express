package com.bilalyesfi.store.entrypoint.advice;

import com.bilalyesfi.store.domain.exception.ProductException;
import com.bilalyesfi.store.domain.exception.ResourceNotFoundException;
import com.bilalyesfi.store.entrypoint.dto.ResponseDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler
  public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex){
    ResponseDTO<?> responseDTO = ResponseDTO.builder()
        .status(HttpStatus.BAD_REQUEST)
        .message("Failed")
        .body("Constraint violation exception")
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
  }

  @ExceptionHandler
  public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
    ResponseDTO<?> responseDTO = ResponseDTO.builder()
        .status(HttpStatus.CONFLICT)
        .message("Failed")
        .body("Data integrity violation exception")
        .build();

    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
  }

  @ExceptionHandler
  public ResponseEntity<?> handleProductExceptionException(ProductException ex){
    ResponseDTO<?> responseDTO = ResponseDTO.builder()
            .status(HttpStatus.PRECONDITION_FAILED)
            .message("Failed!")
            .body(ex.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(responseDTO);
  }

  @ExceptionHandler
  public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
    ResponseDTO<?> responseDTO = ResponseDTO.builder()
            .status(HttpStatus.NOT_FOUND)
            .message("Failed!")
            .body(ex.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
  }

}
