package com.example.backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping

    ApiReponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiReponse<UserResponse> apiReponse = new ApiReponse<>();
        apiReponse.setResult(userService.createUser(request));
        return apiReponse;
    }
    @GetMapping
    ApiReponse<List<UserResponse>> getUsers(){
        var authenlication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}",authenlication.getName());
        authenlication.getAuthorities().forEach(grantedAuthority->log.info(grantedAuthority.getAuthority()));
        return ApiReponse.<List<UserResponse>>builder().result(userService.getListUsers()).build();
    }
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") Long userId){
        return userService.findUserId(userId);
    }
    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable Long userId,@RequestBody UserUpdateRequest request){
        return userService.updateUserId(userId, request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return "user has been deleted";
    }
}
