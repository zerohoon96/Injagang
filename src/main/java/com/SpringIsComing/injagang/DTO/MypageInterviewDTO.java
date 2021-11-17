package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MypageInterviewDTO {

    private Long id;

    private String title;

    private int questionNum;

    private int feedbackNum;

    private LocalDateTime createTime;
}
