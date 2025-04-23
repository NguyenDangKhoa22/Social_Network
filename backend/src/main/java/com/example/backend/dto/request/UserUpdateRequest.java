package com.example.backend.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.validator.DobConstraint;

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
public class UserUpdateRequest {
    String username;
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min = 16,message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
    
}
