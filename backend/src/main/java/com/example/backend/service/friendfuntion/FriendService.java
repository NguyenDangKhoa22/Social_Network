package com.example.backend.service.friendfuntion;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.friendfuction.FriendDTORequest;
import com.example.backend.dto.response.friendfuction.FriendActionDTOResponse;
import com.example.backend.entity.FriendRequest;
import com.example.backend.entity.User;
import com.example.backend.enums.Status;
import com.example.backend.helper.JwtUtils;
import com.example.backend.mapper.friendfunction.FriendActionMapper;
import com.example.backend.repository.FriendRequestRepository;
import com.example.backend.repository.UserRepository;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class FriendService {
    JwtUtils jwtUtils;
    UserRepository userRepository;
    FriendActionMapper friendMapper;
    FriendRequestRepository friendRepository;

    public FriendActionDTOResponse sendInvite(FriendDTORequest request, String token) throws ParseException, JOSEException{
        Long senderId = jwtUtils.getUserId(token);
        User sender = userRepository.findById(senderId)
        .orElseThrow(()-> new RuntimeException("Sender not found"));
        
        User receiver = userRepository.findById(request.getReceiverid())
        .orElseThrow(()-> new RuntimeException("receiver not found"));
        
        FriendRequest friendRequest = FriendRequest.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .status(Status.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

        friendRepository.save(friendRequest);
        return friendMapper.toFriendDTOResponse(friendRequest);
    }
    
}
