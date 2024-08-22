package com.pallow.pallow.domain.chat.repository;

import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByName(String name);


    @Query("SELECT cr FROM ChatRoom cr JOIN cr.userAndChatRooms ucr " +
            "WHERE ucr.user1 IN :users OR ucr.user2 IN :users " +
            "GROUP BY cr " +
            "HAVING COUNT(DISTINCT ucr) = :userCount")
    Optional<ChatRoom> findByUsersIn(@Param("users") List<User> users, @Param("userCount") long userCount);
    @Query("SELECT DISTINCT cr FROM ChatRoom cr JOIN cr.userAndChatRooms ucr WHERE ucr.user1 = :user OR ucr.user2 = :user")
    List<ChatRoom> findAllByUser(@Param("user") User user);

    @Query("SELECT DISTINCT cr FROM ChatRoom cr JOIN cr.userAndChatRooms ucr WHERE (ucr.user1 = :user OR ucr.user2 = :user) AND (ucr.user1.status = 'ACTIVE' OR ucr.user2.status = 'ACTIVE')")
    List<ChatRoom> findAllByActiveUser(@Param("user") User user);

    List<ChatRoom> findByNameContaining(String keyword);

    List<ChatRoom> findByCreatedAtAfter(java.time.LocalDateTime date);

    @Query("SELECT cr FROM ChatRoom cr WHERE SIZE(cr.userAndChatRooms) >= :count")
    List<ChatRoom> findByUserCountGreaterThanEqual(@Param("count") int count);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserAndChatRoom uacr WHERE uacr.chatRoom = :chatRoom AND (uacr.user1 = :user OR uacr.user2 = :user)")
    void deleteUserFromChatRoom(@Param("chatRoom") ChatRoom chatRoom, @Param("user") User user);
}