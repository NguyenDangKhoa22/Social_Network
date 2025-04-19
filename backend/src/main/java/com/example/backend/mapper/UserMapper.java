package com.example.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.dto.response.IntroSpectResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    @Mapping(target = "senders", source = "user.sentRequests")
    @Mapping(target = "receivers", source = "user.receiverRequests")
    UserResponse toUserReponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    @Mapping(target = "valid", source = "valid")
    @Mapping(target = "userId", source = "user.id")
    IntroSpectResponse toIntroSpectResponse(User user, boolean valid); 
    
}
