package com.example.backend.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.example.backend.dto.response.friendfuction.FriendDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<RoleReponse> roles;
    Set<FriendDTO> senders;
    Set<FriendDTO> receivers;
}
