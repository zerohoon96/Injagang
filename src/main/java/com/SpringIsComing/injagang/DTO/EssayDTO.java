package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

//자소서 게시판에서 보여줄 목록
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayDTO {
    private Long pk;
    private String title;
    private Integer qCnt, fCnt, rCnt;
    private String writer;
    private LocalDateTime regDate, modDate;
    private List<String> contentTitle, contentText; //자소서 각 항목의 질문, 내용
}
