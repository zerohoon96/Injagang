package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//게시물 보기에서 사용할 예상질문 DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long pk;
    private Long contentId;
    private String nickname; //예상질문을 단 사람의 닉네임
    private String content; //예상질문 내용
    private LocalDateTime date;
}
