package com.example.backend.service.invite;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.send_invitation.UserSendInvitation;
import com.example.backend.dto.response.receiverinvitation.UserReceiverInvitation;
import com.example.backend.mapper.InviteMapper;
import com.example.backend.repository.InviteRepository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InviteService {
    InviteRepository inviteRepository;
    InviteMapper inviteMapper;
    public UserReceiverInvitation create (UserSendInvitation request){
        var invite = inviteMapper.toFriend(request);
        invite = inviteRepository.save(invite);
        return inviteMapper.toUserReceiverInvitation(invite);
    }

    public List<UserReceiverInvitation> getAll (UserSendInvitation request){
        var invite = inviteRepository.findAll();
        return invite.stream().map(inviteMapper::toUserReceiverInvitation).toList();
    }

    public void delete (Long id){
        inviteRepository.deleteById(id);
    }
}
