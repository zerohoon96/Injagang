package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.MypageInterviewDTO;
import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface InterviewService {

    List<Interview> findInterviews(Member member);


    default MypageInterviewDTO toMypageInterViewDTO(Interview interview){

        return MypageInterviewDTO.builder()
                .id(interview.getId())
                .title(interview.getTitle())
                .feedbackNum(1)
                .questionNum(1)
                .createTime(LocalDateTime.now())
                .build();
    }

}
