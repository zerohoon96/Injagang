package com.SpringIsComing.injagang.DTO;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayFeedbackComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EssayFeedbackDTO {
    private Long id; //feedback id
    private Essay essay; //자기소개서 내용
    private List<EssayFeedbackComment> feedbackComments; //피드백 댓글 목록
    private String comment; //총평
    private LocalDateTime time; //작성 시작
}