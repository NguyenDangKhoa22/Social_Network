package com.example.backend.exeption;

public enum ErrorCode {
    USER_EXITED(404,"user exited"),
    INVALID_KEY(1000,"Invalid message key"),
    UNCATEGORIZED_EXEPTION(999,"uncategorize error"),
    USERNAME_VALIDATE(405,"username must be at least 5 characters "),
    PASSWORD_VALIDATE(405,"Password must be at least 8 characters");
    
        private int code;
        private String message;
        
        ErrorCode(int code, String message) {
            this.code = code;
            this.message = message;
        }
        public int getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
    
}
