// package com.example.backend.service.invite;

// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import com.example.backend.dto.request.send_invitation.UserSendInvitation;
// import com.example.backend.dto.response.receiverinvitation.UserReceiverInvitation;
// import com.example.backend.entity.InviteFriend;
// import com.example.backend.entity.User;
// import com.example.backend.exeption.AppExeption;
// import com.example.backend.exeption.ErrorCode;
// import com.example.backend.repository.InviteRepository;
// import com.example.backend.repository.UserRepository;

// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;

// @Service
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class UserSenderService {
//     private InviteRepository inviteRepository;
//     private UserRepository userRepository;

//     public UserReceiverInvitation<String> sendInvitation(UserSendInvitation request){
//         // Bước 1: Kiểm tra người gửi và người nhận có tồn tại không
//         Optional<User> senderOptional = userRepository.findById(request.getSenderId());
//         Optional<User> receiOptional = userRepository.findById(request.getReceiverId());

//         if (senderOptional.isEmpty()|| receiOptional.isEmpty()) {
//             throw new AppExeption(ErrorCode.USERID_NOT_EXITTED);
//         }

//         // Bước 2: Tạo bản ghi InviteFriend và lưu vào cơ sở dữ liệu
//         InviteFriend inviteFriend = InviteFriend.builder()
//             .userInvite(request.getSenderId())
//             .userReceive(request.getReceiverId())
//             .status("PENDING")
//             .message(request.getMessage())
//             .build();

//         inviteRepository.save(inviteFriend);
//         // Bước 3: Trả về thông tin phản hồi
//         return UserReceiverInvitation.<String>builder()
//                     .senderId(request.getSenderId())
//                     .receiverId(request.getReceiverId())
//                     .status("PENDING")
//                     .message(request.getMessage())
//                     .build();
//     }
// }
