package com.ssafy.gumibom.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.gumibom.domain.meeting.entity.MeetingMember;
import com.ssafy.gumibom.domain.meetingPost.entity.MeetingApplier;
import com.ssafy.gumibom.domain.meetingPost.entity.MeetingPost;
import com.ssafy.gumibom.domain.pamphlet.entity.PersonalPamphlet;
import com.ssafy.gumibom.global.common.Category;
import com.ssafy.gumibom.global.common.Nation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String email;
    private String nickname;
    private boolean gender;
    private String birth;
    private String phone;
    private String imgURL;
    private double belief;
    private String town;
    private String fcmtoken;

    @Embedded
    private Nation nation;

    @ElementCollection
    private List<Category> categories;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonalPamphlet> personalPamphlets;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MeetingPost> meetingPosts;

    @OneToOne(fetch = FetchType.LAZY)
    private MeetingApplier meetingApplier;

    @OneToOne(fetch = FetchType.LAZY)
    private MeetingMember meetingMember;

    public User(String loginId, String password, String nickname, String birth, String phone, boolean gender, List<Category> categories, Nation nation) {
    }

    public void passwordEncoding(){

    }

    public void setPassword(String password) {
        this.password = password;
    }
}
