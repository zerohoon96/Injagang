package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.MypageInterviewDTO;
import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.Video;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface InterviewService {

    List<Interview> findInterviews(Member member);

    void getEssays(Map<String, Integer> essayMap, String nickname);

    void getRandomExpectedQuestions(List<String> questionList, int cnt, String nickname);

    default MypageInterviewDTO toMypageInterViewDTO(Interview interview){

        List<Video> videos = interview.getVideos();
        int cnt = videos.stream().mapToInt(video -> video.getFeedbacks().size()).sum();

        return MypageInterviewDTO.builder()
                .id(interview.getId())
                .title(interview.getTitle())
                .questionNum(videos.size())
                .feedbackNum(cnt)
                .createTime(LocalDateTime.now())
                .build();
    }

}
