package com.example.backend.mapper.friendfunction;

import org.mapstruct.Mapper;

import com.example.backend.dto.request.friendfuction.FriendDTORequest;
import com.example.backend.dto.response.friendfuction.FriendActionDTOResponse;
import com.example.backend.dto.response.friendfuction.FriendActionResponse;
import com.example.backend.entity.FriendRequest;

@Mapper(componentModel = "spring", uses = FriendDTOMapper.class)
public interface FriendActionMapper {
    FriendRequest toFriendRequest(FriendDTORequest request);

    FriendActionDTOResponse toFriendDTOResponse(FriendRequest response);

    FriendActionResponse toFriendActionResponse(FriendRequest friendRequest);
}
