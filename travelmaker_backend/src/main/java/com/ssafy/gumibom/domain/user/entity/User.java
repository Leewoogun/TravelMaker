package com.ssafy.gumibom.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.gumibom.domain.meeting.entity.MeetingMember;
import com.ssafy.gumibom.domain.meetingPost.entity.MeetingApplier;
import com.ssafy.gumibom.domain.pamphlet.entity.PersonalPamphlet;
import com.ssafy.gumibom.global.common.Category;
import com.ssafy.gumibom.global.common.Nation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    @Email
    private String email;

    @NotEmpty
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender; // Gender는 enum 타입

    private String birth;

    @NotEmpty
    @Size(min = 10, max = 15)
    private String phone;

    @Lob
    private String profileImgURL;;

    private Double trust;

    private String town;

    @Lob
    private String fcmtoken;

    private String nation;

    @ElementCollection
    private List<String> categories;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonalPamphlet> personalPamphlets;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<MeetingPost> meetingPosts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingApplier> meetingAppliers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MeetingMember> meetingMembers = new ArrayList<>();

//    // Gender enum 타입 정의
//    public enum Gender {
//        MALE, FEMALE
//    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role을 GrantedAuthority로 변환
        GrantedAuthority authority = new SimpleGrantedAuthority(role.name());

        // 변환된 GrantedAuthority를 담은 컬렉션 반환
        return Collections.singletonList(authority);
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

//    @Builder
//    public User(SignupRequestDto signupRequestDto){
//        this.username = signupRequestDto.getUsername();
//        this.password = signupRequestDto.getPassword();
//        this.email = signupRequestDto.getEmail();
//        this.gender = signupRequestDto.getGender();
//        this.phone = signupRequestDto.getPhone();
//        this.nation = signupRequestDto.getNation();
//        this.categories = signupRequestDto.getCategories();
//        this.imgURL = signupRequestDto.getProfileImgURL();
//    }
//
//    //token 생성시 사용될 생성자
//    @Builder
//    public User(String username, String password, Role role) {
//        this.username = username;
//        this.password = password;
//        this.role = role;
//    }

//    public User(String loginId, String password, String nickname, String birth, String phone, boolean gender, List<Category> categories, Nation nation) {
//    }
//
//    public User(String subject, String password, Collection<? extends GrantedAuthority> authorities) {
//    }
//
//
//    public void setPassword(String password) {
//        this.password = password;
//    }


//    @JsonIgnore
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<MeetingPost> meetingPosts;

}
