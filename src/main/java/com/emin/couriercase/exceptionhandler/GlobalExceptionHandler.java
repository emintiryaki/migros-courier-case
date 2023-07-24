package com.emin.couriercase.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.emin.couriercase.model.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {
    private final HttpServletRequest httpServletRequest;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .details(List.of(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(httpServletRequest.getRequestURI())
                .ipAddress(httpServletRequest.getRemoteAddr())
                .error("INTERNAL_SERVER_ERROR").build();
        log.error("An error occurred : {}", errorResponseDto);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<ErrorResponseDto> validationException(ConstraintViolationException ex) {

        List<String> errors = ex.getConstraintViolations().stream()
                .map(e -> e.getPropertyPath().toString() + " : " + e.getMessage()).toList();

        ErrorResponseDto response = ErrorResponseDto.builder()
                .details(errors)
                .status(HttpStatus.BAD_REQUEST.value())
                .ipAddress(httpServletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .error("validation_error")
                .path(httpServletRequest.getRequestURI())
                .build();
        log.warn("Validation exception occurred : {}", response);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ErrorResponseDto> entityNotFoundExceptionHandler(Exception exception,
            HttpServletRequest httpServletRequest) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .details(List.of(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NO_CONTENT.value())
                .path(httpServletRequest.getRequestURI())
                .ipAddress(httpServletRequest.getRemoteAddr())
                .error(exception.getMessage()).build();
        log.warn("A warning occurred : {}", errorResponseDto);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
