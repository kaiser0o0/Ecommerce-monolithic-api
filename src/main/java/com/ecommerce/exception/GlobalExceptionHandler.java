package com.ecommerce.exception;

import com.ecommerce.common.Result;
import com.ecommerce.common.constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        // Lambda expression kullanarak validasyon hatalarını string olarak birleştirme
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(constants.VALIDATION_ERROR + " - " + errors));
    }
}