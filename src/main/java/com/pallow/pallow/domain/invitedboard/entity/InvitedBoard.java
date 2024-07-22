package com.pallow.pallow.domain.invitedboard.entity;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.InviteStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class InvitedBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private InviteStatus status = InviteStatus.WAITING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meet_id")
    private Meets meets;

    public void acceptInvite() {
        this.status = InviteStatus.ACCEPTED;
    }

    public void rejectInvite() {
        this.status = InviteStatus.DECLINED;
    }
}
