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
public class EssayFeedbackQuestionDTO { //문제별
    private List<EssayFeedbackCommentDTO> commentList = new ArrayList<EssayFeedbackCommentDTO>();
}