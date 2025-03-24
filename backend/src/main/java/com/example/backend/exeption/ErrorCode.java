package com.example.backend.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXEPTION(9999,"uncategorize error",HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001,"Invalid message key",HttpStatus.BAD_REQUEST),
    USER_EXITED(1002,"user exited",HttpStatus.BAD_REQUEST),
    USERNAME_VALIDATE(1003,"username must be at least 5 characters ",HttpStatus.BAD_REQUEST),
    PASSWORD_VALIDATE(1004,"Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EXITED(1005,"User not exited",HttpStatus.NOT_FOUND),
    USERID_NOT_EXITTED(1008,"Id not exited",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006,"unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"you do not have permission",HttpStatus.FORBIDDEN);

    
        private int code;
        private String message;
        private HttpStatusCode statusCode;
        
        ErrorCode(int code, String message, HttpStatusCode statusCode) {
            this.code = code;
            this.message = message;
            this.statusCode = statusCode;
        }
    
}
