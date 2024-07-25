package com.pallow.pallow.domain.user.entity;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.Role;
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
    @JoinColumn(name = "profile_id") // ,nullable = false
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

    @Column // (nullable = false)
    private String position;

    // 유저 Soft Delete Entity 수정 있습니다.
    @Column
    private LocalDate deletedAt;

    // 유저 create 수정 있습니다.
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
