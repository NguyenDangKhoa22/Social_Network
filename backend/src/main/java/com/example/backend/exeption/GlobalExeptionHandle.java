package com.example.backend.exeption;


import java.util.Map;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.backend.dto.request.ApiReponse;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExeptionHandle {

    private static final String MIN_ATTRIBUTE = "min";

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiReponse> handlingRunTimeExeption(RuntimeException exception){
        log.error("Unhandled exception: ", exception);
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
        Map<String, Object> attributes = null;
        try{
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation = exception.getBindingResult()
                    .getAllErrors().get(0).unwrap(ConstraintViolation.class);

            attributes =  constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());
        }catch(IllegalArgumentException i){

        }
       
        ApiReponse apiReponse = new ApiReponse<>();
        apiReponse.setCode(errorCode.getCode());
        apiReponse.setMessage(Objects.nonNull(attributes) ? 
                mapAttribute(errorCode.getMessage(), attributes) :
                errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes){
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{"+ MIN_ATTRIBUTE+"}",minValue);
    }
    
}
