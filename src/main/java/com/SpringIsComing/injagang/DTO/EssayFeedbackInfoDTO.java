package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class EssayFeedbackInfoDTO { //넘길 객체
    private List<String> questions; //자기소개서 질문
    private List<String> answers; //자기소개서 답변
    private List<EssayFeedbackQuestionDTO> everyComment = new ArrayList<EssayFeedbackQuestionDTO>(); //문제별 첨삭
    private String content; //총평
}