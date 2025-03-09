package com.example.backend.exeption;

public enum ErrorCode {
    USER_EXITED(404,"user exited"),
    UNCATEGORIZED_EXEPTION(999,"uncategorize error");
    
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
