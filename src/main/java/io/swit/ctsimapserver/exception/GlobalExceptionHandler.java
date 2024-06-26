package io.swit.ctsimapserver.exception;

import io.swit.ctsimapserver.model.dto.com.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleCustomException(CustomException ex) {
        ApiResponse<T> apiResponse = ApiResponse.setApiResponse(false, ex.getMessage(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
