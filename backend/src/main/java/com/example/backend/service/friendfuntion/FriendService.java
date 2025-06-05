package com.example.backend.service.friendfuntion;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.friendfuction.FriendActionRequest;
import com.example.backend.dto.request.friendfuction.FriendDTORequest;
import com.example.backend.dto.response.friendfuction.FriendActionDTOResponse;
import com.example.backend.dto.response.friendfuction.FriendActionResponse;
import com.example.backend.dto.response.friendfuction.FriendDTO;
import com.example.backend.dto.response.friendfuction.FriendReceiverDTOResponse;
import com.example.backend.dto.response.friendfuction.FriendSenderDTOResponse;
import com.example.backend.entity.FriendRequest;
import com.example.backend.entity.User;
import com.example.backend.enums.Status;
import com.example.backend.helper.JwtUtils;
import com.example.backend.mapper.friendfunction.FriendActionMapper;
import com.example.backend.mapper.friendfunction.FriendDTOMapper;
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
    FriendDTOMapper friendDTOMapper;

    // Send a friend request
    public FriendActionDTOResponse sendInvite(FriendDTORequest request, String token) throws ParseException, JOSEException{
        Long senderId = jwtUtils.getUserId(token);
        User sender = userRepository.findById(senderId)
        .orElseThrow(()-> new RuntimeException("Sender not found"));
        
        User receiver = userRepository.findById(request.getReceiverid())
        .orElseThrow(()-> new RuntimeException("receiver not found"));

        Optional<FriendRequest> existingRequest = friendRepository.findExistingRequestBetween(receiver.getId(), senderId);
        
        if (existingRequest.isPresent()) {
            throw new RuntimeException("A friend request already exists between there users");
        }

        if(senderId.equals(request.getReceiverid())){
            throw new RuntimeException("You cannot send a friend request to yourself");
        }
        
        FriendRequest friendRequest = FriendRequest.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .status(Status.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();
        friendRepository.save(friendRequest);
        
        return friendMapper.toFriendDTOResponse(friendRequest);
    }

    // Get the list of friend receiver 
    public List<FriendReceiverDTOResponse> getReceivedInvite(String token) throws ParseException, JOSEException{
        Long userId = jwtUtils.getUserId(token);
        User receiver = userRepository.findById(userId)
        .orElseThrow(()-> new RuntimeException("receiver not found"));
        
        List<FriendRequest> requests = friendRepository.findBySenderAndStatus(receiver, Status.PENDING);

        return requests.stream().map(friendMapper::toFriendReceiverDTOResponse).collect(Collectors.toList());
    }
    //Get the list of friend invite
    public List<FriendSenderDTOResponse> getSenderInvite(String token) throws ParseException,JOSEException{
         Long userId = jwtUtils.getUserId(token);
        User receiver = userRepository.findById(userId)
        .orElseThrow(()-> new RuntimeException("receiver not found"));
        
        List<FriendRequest> requests = friendRepository.findByReceiverAndStatus(receiver, Status.PENDING);

        return requests.stream().map(friendMapper::toFriendSenderDTOResponse).collect(Collectors.toList());
    }

    // reponse to a friend request
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
        }else{
            throw new IllegalArgumentException("Invalid action: "+ action);
        }

        friendRepository.save(friendRequest);

        return friendMapper.toFriendActionResponse(friendRequest);

    }
    public List<FriendDTO> friendUser(String token) throws ParseException, JOSEException{
        Long userId = jwtUtils.getUserId(token);

        User user = userRepository.findById(userId)
        .orElseThrow(()->new RuntimeException("User not found"));

        List<FriendRequest> listFriends = friendRepository.findFriendUser(Status.ACCEPTED, user);
        System.out.println("Number of accepted friendships found: " + listFriends.size());

        return listFriends.stream()
        .map(req ->friendDTOMapper.toFriendDTO(getFriendFromRequest(req, userId)))
        .collect(Collectors.toList());
    }
    public User getFriendFromRequest(FriendRequest req, Long currentId){
        return req.getSender().getId().equals(currentId) ?req.getReceiver():req.getSender();
    }
}
