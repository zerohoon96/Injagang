package com.SpringIsComing.injagang.DTO;

import lombok.Data;

import java.util.List;

@Data
public class EssayFeedbackReadDTO {
    private List<Integer> num;
    private List<Integer> start;
    private List<Integer> end;
    private List<String> comment;
    private List<String> questions;
    private List<String> answers;
    private String essayPostName;
    private String essayWriter;
    private String feedbackWriter;
    private String content;
    private String curUserNickname;
    private Long feedbackId;
    private Long essayId;

}
