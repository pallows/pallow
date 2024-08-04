package com.pallow.pallow.domain.user.entity;

import com.pallow.pallow.domain.chat.entity.UserAndChatRoom;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.global.common.CommonOauth;
import com.pallow.pallow.domain.user.dto.SignupRequestDto;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.Gender;
import com.pallow.pallow.global.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;


@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    @Column
    private CommonOauth oauth;

    @OneToMany(mappedBy = "groupCreator", fetch = FetchType.LAZY)
    private List<Meets> meets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAndChatRoom> userAndChatRooms = new ArrayList<>();

    public User(Long id, Profile profile, String username, String password, String email,
            String nickname, Role userRole, String name, List<Meets> meets, Gender gender) {
        this.id = id;
        this.profile = profile;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
        this.meets = meets;
        this.name = name;
        this.gender = gender;
        this.userAndChatRooms = new ArrayList<>();
    }

    private User(SignupRequestDto dto, String encryptedPassword) {
        username = dto.getUsername();
        password = encryptedPassword;
        name = dto.getName();
        email = dto.getEmail();
    }

    public void softDeleteUser() {
        this.status = CommonStatus.DELETED;
    }

    public void updateUser(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public static User of(SignupRequestDto dto, String encodedPassword) {
        return new User(dto, encodedPassword);
    }

}