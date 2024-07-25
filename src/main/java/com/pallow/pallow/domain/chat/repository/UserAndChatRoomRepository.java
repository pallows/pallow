package com.pallow.pallow.domain.chat.repository;

import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.chat.entity.UserAndChatRoom;
import com.pallow.pallow.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAndChatRoomRepository extends JpaRepository<UserAndChatRoom, Long> {
    Optional<UserAndChatRoom> findByUserAndChatRoom(User user, ChatRoom chatRoom);

    @Query("SELECT uacr FROM UserAndChatRoom uacr WHERE uacr.user = :user AND uacr.chatRoom = :chatRoom")
    Optional<UserAndChatRoom> findByUserAndChatRoomOptimized(@Param("user") User user, @Param("chatRoom") ChatRoom chatRoom);

    boolean existsByUserAndChatRoom(User user, ChatRoom chatRoom);

    List<UserAndChatRoom> findByUserAndIsActiveTrue(User user);
}