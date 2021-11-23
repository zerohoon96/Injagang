package com.SpringIsComing.injagang.DTO;

import com.SpringIsComing.injagang.Entity.ExpectedQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//게시물 보기에서 사용할 DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayBoardDTO {
    private Long pk;
    private String title; //게시물 제목
    private String essayTitle; //자소서 제목
    private String text; //게시물 글
    private List<EssayContentDTO> contentList; //항목들
    private List<EssayFeedbackDTO> feedbackList; //피드백들
    private List<QuestionDTO> questionList; //예상질문들
    private List<ReplyDTO> replyList; //댓글들
    private int fCnt; //피드백 개수
}
