package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//면접 게시물 보기에서 사용할 DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBoardDTO {
    private Long pk;
    private String writer; //글쓴이 닉네임
    private String title; //게시물 제목
    private String text; //게시물 내용
    private List<VideoDTO> videoList;
    private List<InterviewFeedbackDTO> feedbackList;
    private List<ReplyDTO> replyList;
    private int fCnt;
}
