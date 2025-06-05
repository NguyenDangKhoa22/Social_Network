package com.example.backend.controller.friendfuction;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.friendfuction.FriendActionRequest;
import com.example.backend.dto.request.friendfuction.FriendDTORequest;
import com.example.backend.dto.response.friendfuction.FriendActionDTOResponse;
import com.example.backend.dto.response.friendfuction.FriendActionResponse;
import com.example.backend.dto.response.friendfuction.FriendDTO;
import com.example.backend.dto.response.friendfuction.FriendReceiverDTOResponse;
import com.example.backend.dto.response.friendfuction.FriendSenderDTOResponse;
import com.example.backend.service.friendfuntion.FriendService;
import com.nimbusds.jose.JOSEException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FriendController {
    FriendService friendService;

    @PostMapping("/invite")
    public ApiReponse<FriendActionDTOResponse> sendFriendRequest(@RequestBody FriendDTORequest request,
            HttpServletRequest httpServletRequest) throws ParseException, JOSEException {
        
        String token = extractToken(httpServletRequest);
        
        return ApiReponse.<FriendActionDTOResponse>builder().result(friendService.sendInvite(request, token)).build();
    }
    @GetMapping("/getreceiver")
    public ApiReponse<List<FriendReceiverDTOResponse>> getReceiver(HttpServletRequest httpServletRequest) 
        throws ParseException, JOSEException {
        String token = extractToken(httpServletRequest); 
        
        return ApiReponse.<List<FriendReceiverDTOResponse>>builder().result(friendService.getReceivedInvite(token)).build();
    }

    @GetMapping("/getinvite")
    public ApiReponse<List<FriendSenderDTOResponse>> getSender(HttpServletRequest httpServletRequest) 
        throws ParseException, JOSEException
        {
            String token = extractToken(httpServletRequest);
            return ApiReponse.<List<FriendSenderDTOResponse>>builder().result(friendService.getSenderInvite(token)).build();
        }
    

    @PostMapping("/action")
    public ApiReponse<FriendActionResponse> responseInvite(@RequestBody FriendActionRequest request,
    HttpServletRequest httpServletRequest) 
    throws ParseException, JOSEException{
        String token = extractToken(httpServletRequest);
        
        return ApiReponse.<FriendActionResponse>builder().result(friendService.responseInvite(request, token)).build();
    }

    @GetMapping("/getfriend")
    public ApiReponse<List<FriendDTO>> getfriends(HttpServletRequest httpServletRequest) throws ParseException, JOSEException {
        String token = extractToken(httpServletRequest);
        return ApiReponse.<List<FriendDTO>>builder().result(friendService.friendUser(token)).build();
    }
    
    private String extractToken(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if(bearer != null && bearer.startsWith(bearer)){
            return bearer.substring(7);
        }
        throw new RuntimeException("Missing or invalid Authorization header");
    }

    
}
