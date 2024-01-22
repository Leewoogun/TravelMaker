package com.ssafy.gumibom.domain.record.entity;


import com.ssafy.gumibom.domain.pamphlet.entity.PersonalPamphlet;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("P")
public class PersonalRecord extends Record {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppamphlet_id")
    private PersonalPamphlet personalPamphlet;

    private long userId; // 임시 데이터 타입
}
