package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//게시물 보기에서 사용할 항목 DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayContentDTO {
    private Long pk;
    private String title; //항목의 질문
    private String text; //답변 내용
}
