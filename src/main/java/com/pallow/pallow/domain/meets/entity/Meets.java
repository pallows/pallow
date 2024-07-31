package com.pallow.pallow.domain.meets.entity;

import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.InviteStatus;
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

    private String image;

    private long memberCount;

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
    public Meets(String title, String content, String image, String position,
            CommonStatus status, User user) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.memberCount = calculatedMemberList().size();
        this.position = position;
        this.status = status;
        this.groupCreator = user;
    }

    public void update(MeetsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.image = requestDto.getImage();
    }

    public void delete() {
        this.status = CommonStatus.DELETED;
    }

    // todo - 사용하지 않는 매서드
    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        if (chatRoom != null) {
            chatRoom.setMeets(this);
        }
    }

    // 멤버 리스트의 수를 계산하는 메서드
    private List<InvitedBoard> calculatedMemberList() {
        return usersInvitedBoards.stream()
                .filter(invite -> invite.getMeets().getId().equals(id) && invite.getStatus().equals(InviteStatus.ACCEPTED) )
                .toList();
    }

    // 멤버 리스트를 업데이트하는 메서드
    public void updateMemberList() {
        this.memberCount = calculatedMemberList().size();
    }
}