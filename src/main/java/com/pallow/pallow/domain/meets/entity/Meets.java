package com.pallow.pallow.domain.meets.entity;

import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.CommonStatus;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Meets extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private int memberCount;

    private String position;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User groupCreator;

    @OneToMany(mappedBy = "meets", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<InvitedBoard> usersInvitedBoards = new ArrayList<>();

    @OneToMany(mappedBy = "meets")
    private List<MeetsReview> reviews;

    @OneToOne(mappedBy = "meets", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChatRoom chatRoom;

    @Builder
    public Meets(String title, String content, int memberCount, String position,
            CommonStatus status, User user) {
        this.title = title;
        this.content = content;
        this.memberCount = memberCount;
        this.position = position;
        this.status = status;
        this.groupCreator = user;
    }

    public Meets update(MeetsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        return this;
    }

    public void delete() {
        this.status = CommonStatus.DELETED;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        if (chatRoom != null) {
            chatRoom.setMeets(this);
        }
    }
}