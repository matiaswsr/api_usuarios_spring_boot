package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validaciones de DTO - 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> details = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

        ApiError apiError = new ApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Validación fallida",
            "Error en los datos enviados",
            details
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    // Recurso no encontrado - 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {

        ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Recurso no encontrado",
            ex.getMessage(), List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    // Regla de negocio - 409
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex) {

        ApiError apiError = new ApiError(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Conflicto en la operación",
            ex.getMessage(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    // Cualquier otra excepción - 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex) {

        ApiError apiError = new ApiError(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno del servidor",
            "Ha ocurrido un error inesperado",
            List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
