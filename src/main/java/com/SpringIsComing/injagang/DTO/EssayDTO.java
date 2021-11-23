package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

//자소서 게시판에 보여줄 때, 게시물 작성할 때 사용
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayDTO {
    private Long pk;
    private Integer access;
    private String title;
    private String text;
    private String essayTitle;
    private Integer qCnt, fCnt, rCnt;
    private String writer;
    private LocalDateTime date;
    private List<String> contentTitle, contentText; //자소서 각 항목의 질문, 내용
}
