package com.bilalyesfi.store.entrypoint.util;

import com.bilalyesfi.store.entrypoint.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

public class ControllerHelper {

    public static ResponseEntity<ResponseDTO<?>> getBindingErrorResponse(final BindingResult bindingResult){

        ResponseDTO<?> responseDTO = ResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .body(bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining("\n")))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);

    }
}
