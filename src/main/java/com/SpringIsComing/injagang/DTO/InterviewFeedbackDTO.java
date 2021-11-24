package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewFeedbackDTO {
    private Long pk;
    private Long videoId;
    private String nickname;
    private String comment; //피드백 내용
}
