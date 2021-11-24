package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class EssayFeedbackCommentDTO { //한 개의 첨삭
    private int start;
    private int length;
    private String comment;
}