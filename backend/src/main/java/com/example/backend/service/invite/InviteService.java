// package com.example.backend.service.invite;

// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.example.backend.dto.request.send_invitation.InviteRequest;
// import com.example.backend.dto.response.receiverinvitation.InviteResponse;
// import com.example.backend.exeption.AppExeption;
// import com.example.backend.exeption.ErrorCode;
// import com.example.backend.mapper.InviteMapper;
// import com.example.backend.repository.InviteRepository;
// import com.example.backend.repository.UserRepository;

// import lombok.AccessLevel;
// import lombok.Builder;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;

// @Service
// @RequiredArgsConstructor
// @Builder
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class InviteService {
//     UserRepository userRepository;
//     InviteRepository inviteRepository;
//     InviteMapper inviteMapper;

//     public InviteResponse create (InviteRequest request){
//         var sender = userRepository.findById(request.getSenderId())
//                 .orElseThrow(()->new IllegalArgumentException("Người gửi không tồn tại!"));
//         var receiver = userRepository.findById(request.getReceiverId())
//         .orElseThrow(()->new IllegalArgumentException("Người gửi không tồn tại!"));

//         if (inviteRepository.existsBySenderAndReceiver(sender,receiver)) {
//             throw new AppExeption(ErrorCode.USERID_NOT_EXITTED);
//         }

//         var invite = inviteMapper.toInvitetation(request);
//             invite.setSender(sender.getId());
//             invite.setReceiver(receiver.getId());
//             invite = inviteRepository.save(invite);
//         return inviteMapper.toInvitationResponse(invite);
//     }

//     public List<InviteResponse> getAll (){
//         var invite = inviteRepository.findAll();
//         return invite.stream().map(inviteMapper::toInvitationResponse).toList();
//     }

//     public void delete (Long id){
//         inviteRepository.deleteById(id);
//     }
// }
