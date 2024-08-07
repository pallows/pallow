package com.pallow.pallow.domain.chat.repository;

import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.user.entity.User;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByUsersIn(Collection<User> users);
    Optional<ChatRoom> findByInviteCode(String inviteCode);

}
