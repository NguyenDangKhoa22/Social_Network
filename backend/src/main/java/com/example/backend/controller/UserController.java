package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ApiReponse;
import com.example.backend.dto.UserCreationRequest;
import com.example.backend.dto.UserUpdateRequest;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping

    ApiReponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiReponse<User> apiReponse = new ApiReponse<>();
        apiReponse.setResult(userService.creatRequest(request));
        return apiReponse;
    }
    @GetMapping
    List<User> getUsers(){
        return userService.getListUsers();
    }
    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") Long userId){
        return userService.getUserId(userId);
    }
    @PutMapping("/{userId}")
    User updateUser(@PathVariable Long userId,@RequestBody UserUpdateRequest request){
        return userService.updateUserId(userId, request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return "user has been deleted";
    }
}
