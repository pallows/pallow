package com.pallow.pallow.domain.chat.repository;

import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.chat.entity.UserAndChatRoom;
import com.pallow.pallow.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAndChatRoomRepository extends JpaRepository<UserAndChatRoom, Long> {

    @Query("SELECT uacr FROM UserAndChatRoom uacr WHERE uacr.chatRoom = :chatRoom AND (uacr.user1 = :user OR uacr.user2 = :user)")
    Optional<UserAndChatRoom> findByChatRoomAndUser(@Param("chatRoom") ChatRoom chatRoom,
                                                    @Param("user") User user);

    @Query("SELECT uacr FROM UserAndChatRoom uacr WHERE (uacr.user1.id = :userId OR uacr.user2.id = :userId) AND uacr.isActive = true")
    List<UserAndChatRoom> findByUserIdAndIsActiveTrue(@Param("userId") Long userId);

    @Query("SELECT uacr FROM UserAndChatRoom uacr WHERE uacr.chatRoom.id = :roomId AND (uacr.user1.id = :userId OR uacr.user2.id = :userId)")
    Optional<UserAndChatRoom> findByUserIdAndChatRoomId(@Param("userId") Long userId,
                                                        @Param("roomId") Long roomId);

    @Query("DELETE FROM UserAndChatRoom uacr WHERE uacr.chatRoom = :chatRoom AND (uacr.user1 = :user OR uacr.user2 = :user)")
    void deleteByChatRoomAndUser(@Param("chatRoom") ChatRoom chatRoom, @Param("user") User user);

    long countByChatRoom(ChatRoom chatRoom);

    Optional<UserAndChatRoom> findByChatRoomAndUser1OrUser2(ChatRoom chatRoom, User user1,
                                                            User user2);

    Optional<UserAndChatRoom> findByChatRoomAndUser1(ChatRoom chatRoom, User user1);
}