package com.developerteam.techzone.handler;

import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private List<String> addMapValue(List<String> list, String newValue) {
        list.add(newValue);
        return list;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class )
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,List<String>> errorsMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError)error).getField();

            if (errorsMap.containsKey(fieldName)) {
                errorsMap.put(fieldName,addMapValue(errorsMap.get(fieldName),error.getDefaultMessage()));
            }else {
                errorsMap.put(fieldName,addMapValue(new ArrayList<>(),error.getDefaultMessage()));
            }

        });
        return ResponseEntity.badRequest().body(createApiError(errorsMap));
    }

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError> handleBaseException(BaseException ex) {
        return ResponseEntity.badRequest().body(createApiError(ex.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiError> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(createApiError(ex.getMessage()));
    }

    public <E> ApiError<E> createApiError(E errors) {

        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());

        CustomException<E> customException = new CustomException<>();
        customException.setCreateTime(new Date());
        customException.setMessage(errors);
        apiError.setException(customException);
        return apiError;
    }

}
