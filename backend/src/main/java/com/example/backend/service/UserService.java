package com.example.backend.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.exeption.AppExeption;
import com.example.backend.exeption.ErrorCode;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User createRequest(UserCreationRequest request){
        

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppExeption(ErrorCode.USER_EXITED); 

        User user = userMapper.toUser(request);

        return userRepository.save(user);
    }

    public List<User> getListUsers(){
        return userRepository.findAll();
    }

    public UserResponse findUserId(Long id){
        return userMapper.toUserRepository(userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUserId(Long userId,UserUpdateRequest request){

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        
        return userMapper.toUserRepository(userRepository.save(user));
    }
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
