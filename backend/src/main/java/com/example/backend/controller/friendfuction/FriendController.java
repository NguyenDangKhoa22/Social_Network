package com.example.backend.controller.friendfuction;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.friendfuction.FriendActionRequest;
import com.example.backend.dto.request.friendfuction.FriendDTORequest;
import com.example.backend.dto.response.friendfuction.FriendActionDTOResponse;
import com.example.backend.dto.response.friendfuction.FriendActionResponse;
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
    @GetMapping("/getinvite")
    public ApiReponse<List<FriendActionDTOResponse>> getReceiver(HttpServletRequest httpServletRequest) 
        throws ParseException, JOSEException {
        String token = extractToken(httpServletRequest); 
        
        return ApiReponse.<List<FriendActionDTOResponse>>builder().result(friendService.getReceivedInvite(token)).build();
    }

    @PostMapping("/action")
    public ApiReponse<FriendActionResponse> responseInvite(@RequestBody FriendActionRequest request,
    HttpServletRequest httpServletRequest) 
    throws ParseException, JOSEException{
        String token = extractToken(httpServletRequest);
        
        return ApiReponse.<FriendActionResponse>builder().result(friendService.responseInvite(request, token)).build();
    }
    
    
    
    private String extractToken(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if(bearer != null && bearer.startsWith(bearer)){
            return bearer.substring(7);
        }
        throw new RuntimeException("Missing or invalid Authorization header");
    }
}
