package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//게시물 보기에서 사용할 댓글 DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private Long pk;
    private String nickname; //댓글 단 사람의 닉네임
    private String content; //댓글 내용
}
