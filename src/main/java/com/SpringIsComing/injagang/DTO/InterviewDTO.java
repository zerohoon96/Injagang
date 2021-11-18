package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//면접 게시판에서 보여줄 목록
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDTO {
    private Long pk;
    private String title; //제목
    private Integer fCnt, rCnt; //피드백 개수, 댓글 개수
    private String writer; //작성자
    private LocalDateTime regDate, modDate; //등록, 수정날짜
}
