package com.example.backend.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 5, message = "username must be at least 5 characters")
    String username;
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    
}
