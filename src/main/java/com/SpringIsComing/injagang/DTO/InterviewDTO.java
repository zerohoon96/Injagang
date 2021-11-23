package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

//면접 게시판에서 보여줄 때, 게시물 작성할 때 사용
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDTO {
    private Long pk;
    private Integer access;
    private String title; //제목
    private String text; //글 내용
    private Integer fCnt, rCnt; //피드백 개수, 댓글 개수
    private String writer; //작성자 닉네임
    private LocalDateTime date;
    private List<String> videoUrls; //업로드한 비디오들의 저장경로 리스트
}
