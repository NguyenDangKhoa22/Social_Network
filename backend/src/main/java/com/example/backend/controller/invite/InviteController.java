package com.example.backend.controller.invite;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.send_invitation.UserSendInvitation;
import com.example.backend.dto.response.receiverinvitation.UserReceiverInvitation;
import com.example.backend.service.invite.InviteService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/invite")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InviteController {
    InviteService inviteService;
    @PostMapping
    ApiReponse<UserReceiverInvitation> create (UserSendInvitation request){
        return ApiReponse.<UserReceiverInvitation>builder().result(inviteService.create(request)).build();
    }
    

}
