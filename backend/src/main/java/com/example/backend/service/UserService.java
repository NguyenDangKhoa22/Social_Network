package com.example.backend.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request){
        
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppExeption(ErrorCode.USER_EXITED); 

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> role = new HashSet<>();
        role.add(Role.USER.name());
        
        return userMapper.toUserReponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<UserResponse> getListUsers(){
        List<User> users = userRepository.findAll();
        log.info("number of users: {}",users.size());
        return userRepository.findAll().stream().map(userMapper::toUserReponse).toList();
    }

    public UserResponse getMyInfor(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
            ()->new AppExeption(ErrorCode.USERID_NOT_EXITTED));
        return userMapper.toUserReponse(user);
    }

    public UserResponse findUserId(String id){
        return userMapper.toUserReponse(userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUserId(String userId,UserUpdateRequest request){

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        
        if(roles.isEmpty()){
            log.warn("Roles not found for: {}", request.getRoles());
            throw new RuntimeException("Roles not found");
        }
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserReponse(userRepository.save(user));
    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
