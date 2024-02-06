package com.ssafy.gumibom.domain.meetingPost.dto.response;

import com.ssafy.gumibom.domain.meetingPost.entity.MeetingPost;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DetailOfMeetingPostResponseDTO {

    private MeetingPost meetingPost;
    private Long dDay;
    private Boolean isHead;
}
