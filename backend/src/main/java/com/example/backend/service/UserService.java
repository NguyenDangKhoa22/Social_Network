package com.example.backend.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.enums.Role;
import com.example.backend.exeption.AppExeption;
import com.example.backend.exeption.ErrorCode;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createRequest(UserCreationRequest request){
        
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppExeption(ErrorCode.USER_EXITED); 

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> role = new HashSet<>();
        role.add(Role.USER.name());

        user.setRole(role);
        return userMapper.toUserReponse(userRepository.save(user));
    }

    public List<User> getListUsers(){
        return userRepository.findAll();
    }

    public UserResponse findUserId(Long id){
        return userMapper.toUserReponse(userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUserId(Long userId,UserUpdateRequest request){

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        
        return userMapper.toUserReponse(userRepository.save(user));
    }
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
