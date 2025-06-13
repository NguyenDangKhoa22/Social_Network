package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.FriendRequest;
import com.example.backend.entity.User;
import com.example.backend.enums.Status;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long>{

    // Tìm lời mời của người dùng 
    List<FriendRequest> findBySenderAndStatus(User sender,Status status);

    // Tìm Tất cả lời nhận của người dùng
    List<FriendRequest> findByReceiverAndStatus(User receiver,Status status);

    Optional<FriendRequest> findBySenderIdAndReceiverId(String senderId, String receiverId); 

    boolean existsBySender_IdAndReceiver_Id(String senderId, String receiverId);
    //find existing request between 2 user
    @Query("SELECT f FROM FriendRequest f WHERE" +
            "(f.sender.id = :user1 AND f.receiver.id = :user2) OR " +
            "(f.sender.id = :user2 AND f.receiver.id = :user1)")
    Optional<FriendRequest> findExistingRequestBetween(@Param("user1") String user1, @Param("user2") String user2);

    @Query("SELECT f FROM FriendRequest f WHERE f.status = :status AND (f.receiver = :user OR f.sender = :user)")
    List<FriendRequest> findFriendUser(@Param("status") Status status, @Param("user") User user);
    
} 
