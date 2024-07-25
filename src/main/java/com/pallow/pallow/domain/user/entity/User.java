package com.pallow.pallow.domain.user.entity;

import com.pallow.pallow.domain.chat.entity.UserAndChatRoom;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Column
    private String position;

    // 유저 Soft Delete Entity 수정 있습니다.
    @Column
    private LocalDate deletedAt;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Meets> meets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAndChatRoom> userAndChatRooms = new ArrayList<>();

    public User(Long id, Profile profile, String username, String password, String email, String nickname, Role userRole, String position, LocalDate deletedAt, List<Meets> meets) {
        this.id = id;
        this.profile = profile;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
        this.position = position;
        this.deletedAt = deletedAt;
        this.meets = meets;
        this.userAndChatRooms = new ArrayList<>();
    }

    public void addUserAndChatRoom(UserAndChatRoom userAndChatRoom) {
        this.userAndChatRooms.add(userAndChatRoom);
        userAndChatRoom.setUser(this);
    }

    public static User createdUser(String username, String nickname, String email, String password, Role role) {
        User user = new User();
        user.username = username;
        user.nickname = nickname;
        user.password = password;
        user.email = email;
        user.userRole = role;
        return user;
    }

    @OneToMany(mappedBy = "groupCreator", fetch =  FetchType.LAZY)
    private List<Meets> meets = new ArrayList<>();

}