package com.example.backend.exeption;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.backend.dto.request.ApiReponse;


@ControllerAdvice
public class GlobalExeptionHandle {

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiReponse> handlingRunTimeExeption(RuntimeException exception){
        
        ApiReponse apiReponse = new ApiReponse<>();
        apiReponse.setCode(ErrorCode.UNCATEGORIZED_EXEPTION.getCode());
        apiReponse.setMessage(ErrorCode.UNCATEGORIZED_EXEPTION.getMessage());
        
        return ResponseEntity.badRequest().body(apiReponse);
    }
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = AppExeption.class)
    public ResponseEntity<ApiReponse> handlingAppExeption(AppExeption exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiReponse apiReponse = new ApiReponse<>();
        apiReponse.setCode(errorCode.getCode());
        apiReponse.setMessage(errorCode.getMessage());
        
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiReponse);
    }
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiReponse> handingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                             .body(ApiReponse.builder()
                             .code(errorCode.getCode())
                             .message(errorCode.getMessage()).build());
    }


    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiReponse> handlingValidateion(MethodArgumentNotValidException exception){

        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try{
            errorCode = ErrorCode.valueOf(enumKey);
        }catch(IllegalArgumentException i){

        }
       
        ApiReponse apiReponse = new ApiReponse<>();
        apiReponse.setCode(errorCode.getCode());
        apiReponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);
    }
}
