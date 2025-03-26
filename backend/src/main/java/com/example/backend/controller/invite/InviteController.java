// package com.example.backend.controller.invite;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.backend.dto.request.send_invitation.UserSendInvitation;
// import com.example.backend.dto.response.receiverinvitation.UserReceiverInvitation;
// import com.example.backend.service.invite.UserSenderService;

// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;




// @RestController
// @RequestMapping("/invite")
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class InviteController {
//     private UserSenderService userSenderService;
//     @PostMapping("/request")
//     public UserReceiverInvitation<String> requestInvite(@RequestBody  UserSendInvitation request) {
//         return  userSenderService.sendInvitation(request);
//     }
    

// }
