package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.FriendRequest;
import com.example.backend.entity.User;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long>{
    // Tìm lời mời của người dùng 
    List<FriendRequest> findBySender(User sender);

    // Tìm Tất cả lời nhận của người dùng
    List<FriendRequest> findByReceiver(User receiver);

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
    
} 
