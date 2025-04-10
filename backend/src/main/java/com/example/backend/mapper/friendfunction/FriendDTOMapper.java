package com.example.backend.mapper.friendfunction;

import org.mapstruct.Mapper;

import com.example.backend.dto.response.friendfuction.FriendDTO;
import com.example.backend.entity.User;

@Mapper(componentModel = "spring")
public interface FriendDTOMapper {
    FriendDTO toFriendDTO (User user);
} 