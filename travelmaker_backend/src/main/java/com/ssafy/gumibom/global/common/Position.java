package com.ssafy.gumibom.global.common;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Position { // 모임 장소

<<<<<<< HEAD
=======
//    @Id
//    @GeneratedValue
//    @Column(name = "position_id")
    private Long id;

//    @Column(name = "position_name")
>>>>>>> feature/record
    private String name;

    private Double latitude;
    private Double longitude;

    private String town;
}
