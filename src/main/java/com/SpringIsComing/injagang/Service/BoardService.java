package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.Entity.Interview;

import java.util.ArrayList;
import java.util.List;

public interface BoardService {

    Long registerEssay(EssayDTO dto);

    Long registerInterview(InterviewDTO dto);

    //자소서 게시판의 한 페이지 가져오기
    PageResultDTO<EssayDTO, Essay> getEssayList(PageRequestDTO requestDTO);

    //면접 게시판의 한 페이지 가져오기
    PageResultDTO<InterviewDTO, Interview> getInterviewList(PageRequestDTO requestDTO);

    //자소서 게시판의 한 게시물 읽어오기
    EssayBoardDTO readEssayBoard(Long id);

    //면접 게시판의 한 게시물 읽어오기
    InterviewBoardDTO readInterviewBoard(Long id);

    //미구현
    default Essay essayDtoToEntity(EssayDTO dto) {
        Essay entity = Essay.builder()
                .build();

        return entity;
    }

    //미구현
    default Interview interviewDtoToEntity(InterviewDTO dto){

        Interview entity = Interview.builder()
                .build();

        return entity;
    }

    //게시판에서 쓸 자소서 DTO
    default EssayDTO essayEntityToDto(Essay essay) {
        int cnt = 0;
        if (essay.getContents().size() != 0) {
            List<EssayContent> contents = essay.getContents();
            for (EssayContent content : contents) {
                cnt += content.getQuestions().size();
            }
        }

        EssayDTO dto = EssayDTO.builder()
                .pk(essay.getId())
                .title(essay.getTitle())
                .writer(essay.getWriter().getNickname())
                .qCnt(cnt)
                .fCnt(essay.getFeedbacks().size())
                .rCnt(essay.getReplies().size())
                .regDate(essay.getDate())
                .build();

        return dto;
    }

    //게시판에서 쓸 면접 DTO
    default InterviewDTO interviewEntityToDto(Interview interview) {
        InterviewDTO dto = InterviewDTO.builder()
                .pk(interview.getId())
                .title(interview.getTitle())
                .writer(interview.getWriter().getNickname())
                .fCnt(interview.getFeedbacks().size())
                .rCnt(interview.getReplies().size())
                .regDate(interview.getDate())
                .build();

        return dto;
    }

    //게시물에서 쓸 자소서 DTO
    default EssayBoardDTO essayBoardEntityToDto(Essay essay) {
        //자소서 항목 목록 DTO로 변환
        List<EssayContentDTO> contentList = new ArrayList<>();
        List<QuestionDTO> questionList = new ArrayList<>();
        essay.getContents().forEach(essayContent -> {

            essayContent.getQuestions().forEach(question -> {
                questionList.add(QuestionDTO.builder()
                                .pk(question.getId())
                                .nickname(question.getWriter().getNickname())
                                .content(question.getText())
                                .contentId(essayContent.getId())
                                .build());
            });

            contentList.add(EssayContentDTO.builder()
                            .pk(essayContent.getId())
                            .title(essayContent.getTitle())
                            .text(essayContent.getText())
                            .build());
        });

        //feedback목록 DTO로 변환
        List<EssayFeedbackDTO> feedbackList = new ArrayList<>();
        essay.getFeedbacks().forEach(feedback -> {
            feedbackList.add(EssayFeedbackDTO.builder()
                            .pk(feedback.getId())
                            .nickname(feedback.getMember().getNickname())
                            .build());
        });
        if(feedbackList.size() != 0)
            System.out.println("feedbackList is not empty!");

        List<ReplyDTO> replyList = new ArrayList<>();
        essay.getReplies().forEach(reply -> {
            replyList.add(ReplyDTO.builder()
                            .pk(reply.getId())
                            .content(reply.getContent())
                            .nickname(reply.getReplyer().getNickname())
                            .build());
        });

        EssayBoardDTO dto = EssayBoardDTO.builder()
                .pk(essay.getId())
                .title(essay.getTitle())
                .text(essay.getText())
                .contentList(contentList)
                .feedbackList(feedbackList)
                .questionList(questionList)
                .replyList(replyList)
                .fCnt(essay.getFeedbacks().size())
                .build();

        return dto;
    }

    //게시물에서 쓸 면접 DTO
    default InterviewBoardDTO interviewBoardEntityToDto(Interview interview) {
        List<VideoDTO> videoList = new ArrayList<>();
        interview.getVideos().forEach(video -> {
            videoList.add(VideoDTO.builder()
                            .pk(video.getId())
                            .videoName(video.getVideoName())
                            .url(video.getVideoURL())
                            .build());
        });

        List<InterviewFeedbackDTO> feedbackList = new ArrayList<>();
        interview.getFeedbacks().forEach(feedback -> {
            feedbackList.add(InterviewFeedbackDTO.builder()
                            .pk(feedback.getId())
                            .nickname(feedback.getMember().getNickname())
                            .build());
        });

        List<ReplyDTO> replyList = new ArrayList<>();
        interview.getReplies().forEach(reply -> {
            replyList.add(ReplyDTO.builder()
                            .pk(reply.getId())
                            .content(reply.getContent())
                            .nickname(reply.getReplyer().getNickname())
                            .build());
        });

        InterviewBoardDTO dto = InterviewBoardDTO.builder()
                .pk(interview.getId())
                .title(interview.getTitle())
                .text(interview.getText())
                .videoList(videoList)
                .feedbackList(feedbackList)
                .replyList(replyList)
                .fCnt(interview.getFeedbacks().size())
                .build();

        return dto;
    }
}
