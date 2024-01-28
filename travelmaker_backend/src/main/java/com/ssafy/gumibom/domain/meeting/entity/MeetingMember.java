package com.ssafy.gumibom.domain.meeting.entity;


import com.ssafy.gumibom.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MeetingMember {

    @Id @GeneratedValue
    @Column(name = "meeting_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean isNative;

    public void setUser(User user) {
        this.user = user;
    }
}
