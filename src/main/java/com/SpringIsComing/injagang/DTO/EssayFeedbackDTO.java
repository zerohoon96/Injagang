package com.SpringIsComing.injagang.DTO;

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
    private Long essayId; //essay id
    private String essayPostName; //essay 게시물 이름
    private List<String> questions; //자기소개서 질문
    private List<String> answers; //자기소개서 답변
}