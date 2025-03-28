package com.example.backend.controller.invite;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.send_invitation.InviteRequest;
import com.example.backend.dto.response.receiverinvitation.InviteResponse;
import com.example.backend.service.invite.InviteService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/invite")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InviteController {
    InviteService inviteService;
    @PostMapping
    ApiReponse<InviteResponse> create (@RequestBody InviteRequest request){
        return ApiReponse.<InviteResponse>builder().result(inviteService.create(request)).build();
    }
    @GetMapping
    public ApiReponse<List<InviteResponse>> getAll() {
        return ApiReponse.<List<InviteResponse>>builder().result(inviteService.getAll()).build();
    }
    

}
