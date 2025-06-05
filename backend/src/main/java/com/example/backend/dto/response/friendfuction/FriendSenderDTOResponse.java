package com.example.backend.dto.response.friendfuction;

import java.time.LocalDateTime;

import com.example.backend.enums.Status;

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
public class FriendSenderDTOResponse {
    FriendDTO sender;
    Status status;
    LocalDateTime createdAt;
}
