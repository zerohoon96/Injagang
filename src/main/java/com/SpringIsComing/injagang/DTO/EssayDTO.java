package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//자소서 게시판에서 게시물로 클릭하여 넘어갈 때
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayDTO {
    private Long pk;
    private String title;
    private Integer qCnt, fCnt;
    private String writer;
    private LocalDateTime regDate, modDate;
}
