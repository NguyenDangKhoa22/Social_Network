package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Invitation;



public interface InviteRepository extends JpaRepository<Invitation, Long>{
    List<Invitation> findBySenderIdAndReceiverId (Long senderID, Long receiverId);

    List<Invitation> findByReceiverId(Long receiverId);

    List<Invitation> findBySenderId(Long senderId);

    List<Invitation> findByReceiverIdAndStatus(Long receiverId, String status);
} 
