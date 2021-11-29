package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//마이페이지에서 모의면접 기록 보여줄 때 사용
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockInterviewDTO {
    private Long pk;
    private String nickname;
    private String title;
    private Integer qNum;
    private String date;
}
