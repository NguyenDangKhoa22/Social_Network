package com.example.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.backend.dto.request.send_invitation.InviteRequest;
import com.example.backend.dto.response.receiverinvitation.InviteResponse;
import com.example.backend.entity.Invitation;

@Mapper(componentModel = "spring")
public interface InviteMapper {
    @Mapping(target = "sender", ignore = true) // Bỏ qua mapping sender vì cần xử lý riêng
    @Mapping(target = "receiver", ignore = true) // Bỏ qua mapping receiver
    @Mapping(target = "status", constant = "PENDING") // Mặc định status khi tạo mới
    Invitation toInvitetation (InviteRequest request);

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.username", target = "senderUserName")
    @Mapping(source = "receiver.id", target = "receiverId")
    InviteResponse toInvitationResponse(Invitation inviteFriend);

}
