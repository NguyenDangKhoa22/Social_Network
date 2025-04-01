package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Friend;
import com.example.backend.entity.FriendId;
import com.example.backend.entity.User;

public interface FriendRepository extends JpaRepository<Friend,FriendId>{
    List<Friend> findByUser(User user);
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}
