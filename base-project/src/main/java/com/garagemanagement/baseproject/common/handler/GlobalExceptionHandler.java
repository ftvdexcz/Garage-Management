package com.garagemanagement.baseproject.common.handler;


import com.garagemanagement.baseproject.common.constant.StatusCodeResponse;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ResponseError> handleError(RuntimeException e){
        if(e instanceof AppError){
            return ResponseEntity.status(((AppError) e).getStatusCode()).body(ResponseError.responseError((AppError) e));
        }else {
            AppError re = new AppError("Server Internal Error!", StatusCodeResponse.STATUS_RESPONSE_INTERNAL_SEVER_ERROR, "");
            return ResponseEntity.status(re.getStatusCode()).body(ResponseError.responseError(re));
        }
    }

    // handle validation body error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Gson gson = new Gson();
        AppError re = new AppError("Validation Error!", StatusCodeResponse.STATUS_RESPONSE_BAD_REQUEST, gson.toJson(errors));
        return ResponseEntity.status(re.getStatusCode()).body(ResponseError.responseError(re));
    }
}
