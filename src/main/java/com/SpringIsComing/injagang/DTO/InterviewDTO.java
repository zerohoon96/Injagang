package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDTO {
    private Long pk;
    private String title; //제목
    private Integer fCnt; //피드백 개수
    private String writer; //작성자
    private LocalDateTime regDate, modDate; //등록, 수정날짜
}
