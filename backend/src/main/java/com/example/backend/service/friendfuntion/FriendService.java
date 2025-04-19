package com.example.backend.service.friendfuntion;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.friendfuction.FriendActionRequest;
import com.example.backend.dto.request.friendfuction.FriendDTORequest;
import com.example.backend.dto.response.friendfuction.FriendActionDTOResponse;
import com.example.backend.dto.response.friendfuction.FriendActionResponse;
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
        
        sender.getSentRequests().add(friendRequest);
        receiver.getReceiverRequests().add(friendRequest);

        userRepository.save(sender);
        userRepository.save(receiver);
        
        return friendMapper.toFriendDTOResponse(friendRequest);
    }
    public List<FriendActionDTOResponse> getReceivedInvite(String token) throws ParseException, JOSEException{
        Long userId = jwtUtils.getUserId(token);
        User receiver = userRepository.findById(userId)
        .orElseThrow(()-> new RuntimeException("receiver not found"));
        
        List<FriendRequest> requests = friendRepository.findByReceiver(receiver);

        return requests.stream().map(friendMapper::toFriendDTOResponse).collect(Collectors.toList());
    }
    public FriendActionResponse responseInvite(FriendActionRequest request, String token) throws ParseException, JOSEException{
        Long receiverId = jwtUtils.getUserId(token);
        Long senderId = request.getSenderId();
        
        FriendRequest friendRequest = friendRepository
        .findBySenderIdAndReceiverId(senderId, receiverId)
        .orElseThrow(()->new RuntimeException("Friend request no found"));

        String action = request.getStatus();
        if(action.equalsIgnoreCase("ACCEPT")){
            friendRequest.setStatus(Status.ACCEPTED);
        }else if(action.equalsIgnoreCase("REJECT")){
            friendRequest.setStatus(Status.REJECTED);
        }else{{
            throw new IllegalArgumentException("Invalid action: "+ action);
        }}

        friendRepository.save(friendRequest);

        return friendMapper.toFriendActionResponse(friendRequest);

    }
}
