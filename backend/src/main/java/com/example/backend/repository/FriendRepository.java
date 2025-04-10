package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Friend;
import com.example.backend.entity.FriendId;
import com.example.backend.entity.User;

import java.util.List;


@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId>{
    List<Friend> findById(User id);

    //kiểm tra xem 2 user đã là bạn chưa 
    boolean existsByIdUserAndIdFriend(Long userId, Long friendId);
} 