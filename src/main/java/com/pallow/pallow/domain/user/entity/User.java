package com.pallow.pallow.domain.user.entity;

import com.pallow.pallow.domain.chat.entity.UserAndChatRoom;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.global.common.CommonOauth;
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
import jakarta.persistence.JoinColumn;
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
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private CommonStatus status = CommonStatus.ACTIVE;

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

    public void addUserAndChatRoom(UserAndChatRoom userAndChatRoom) {
        this.userAndChatRooms.add(userAndChatRoom);
        userAndChatRoom.setUser(this);
    }

    public static User createdUser(String username, String nickname, String name, String email,
            String password, Gender gender, Role role,CommonOauth commonOauth) {
        User user = new User();
        user.username = username;
        user.nickname = nickname;
        user.password = password;
        user.email = email;
        user.userRole = role;
        user.name = name;
        user.gender = gender;
        user.status = CommonStatus.ACTIVE;
        user.oauth = commonOauth;
        return user;
    }

    public void softDeleteUser() {
        this.status = CommonStatus.DELETED;
    }

    public void updateUser(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

}
//객체의 생성이 복잡하고 필드가 많을 경우: 빌더 패턴을 사용하여 유연하고 가독성 높은 객체 생성.
//단순한 객체 생성 및 서브클래싱을 통한 다형성이 필요한 경우: 팩토리 메서드 패턴을 사용하여 객체 생성 로직을 캡슐화.
