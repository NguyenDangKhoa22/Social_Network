package com.example.backend.mapper;

import org.mapstruct.Mapper;

import com.example.backend.dto.request.send_invitation.UserSendInvitation;
import com.example.backend.dto.response.receiverinvitation.UserReceiverInvitation;
import com.example.backend.entity.InviteFriend;

@Mapper(componentModel = "spring")
public interface InviteMapper {

    InviteFriend toFriend (UserSendInvitation request);

    UserReceiverInvitation toUserReceiverInvitation(InviteFriend inviteFriend);

}
