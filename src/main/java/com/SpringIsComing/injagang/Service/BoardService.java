package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

public interface BoardService {

    Long registerEssay(EssayDTO dto);

    Long registerInterview(InterviewDTO dto);

    List<EssayDTO> getEssays(String nickname);

    //자소서 게시판의 한 페이지 가져오기
    PageResultDTO<EssayDTO, Essay> getEssayList(PageRequestDTO requestDTO);

    //면접 게시판의 한 페이지 가져오기
    PageResultDTO<InterviewDTO, Interview> getInterviewList(PageRequestDTO requestDTO);

    //자소서 게시판의 한 게시물 읽어오기
    EssayBoardDTO readEssayBoard(Long id);

    //면접 게시판의 한 게시물 읽어오기
    InterviewBoardDTO readInterviewBoard(Long id);

    //자소서 게시물에서 예상질문 달기
    void registerExpectedQuestion(Long content_pk, String content, String nickname);

    //자소서 게시물에서 댓글 달기
    void registerEssayReply(Long essay_pk, String content, String nickname);

    //면접 게시물에서 댓글 달기
    void registerInterviewReply(Long pk, String content, String nickname);

    //면접 게시물에서 피드백 달기
    void registerInterviewFeedback(Long video_pk, String content, String nickname);

    //자소서 게시물 제목, 글 수정하기
    void updateEssayBoard(Long pk, String title, String text);

    //면접 게시물 제목, 글 수정하기
    void updateInterviewBoard(Long pk, String title, String text);

    //자소서 게시물 삭제하기
    void deleteEssayBoard(Long pk);

    //면접 게시물 삭제하기
    void deleteInterviewBoard(Long pk);

    //면접 게시물에 달린 예상질문 삭제하기
    void deleteEssayQuestion(Long question_pk);

    //게시물에 달린 댓글 삭제하기
    void deleteBoardReply(Long reply_pk);

    //면접 게시물에 달린 피드백 삭제하기
    void deleteInterviewFeedback(Long feedback_pk);

    //사용 안함
    default Essay essayDtoToEntity(EssayDTO dto) {

        Essay entity = Essay.builder()
                .build();

        return entity;
    }

    //사용 안함
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
                .access(essay.getAccess())
                .qCnt(cnt)
                .fCnt(essay.getFeedbacks().size())
                .rCnt(essay.getReplies().size())
                .date(essay.getDate())
                .build();

        return dto;
    }

    //게시판에서 쓸 면접 DTO
    default InterviewDTO interviewEntityToDto(Interview interview) {
        int cnt = interview.getVideos().stream().mapToInt(video -> video.getFeedbacks().size()).sum();

        InterviewDTO dto = InterviewDTO.builder()
                .pk(interview.getId())
                .title(interview.getTitle())
                .writer(interview.getWriter().getNickname())
                .fCnt(cnt)
                .rCnt(interview.getReplies().size())
                .date(interview.getDate())
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
                                .date(question.getDate())
                                .build());
            });

            contentList.add(EssayContentDTO.builder()
                            .pk(essayContent.getId())
                            .title(essayContent.getQuestion())
                            .text(essayContent.getAnswer())
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
                            .date(reply.getDate())
                            .build());
        });

        EssayBoardDTO dto = EssayBoardDTO.builder()
                .pk(essay.getId())
                .writer(essay.getWriter().getNickname())
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
        List<InterviewFeedbackDTO> feedbackList = new ArrayList<>();
        interview.getVideos().forEach(video -> {

            video.getFeedbacks().forEach(feedback -> {
                feedbackList.add(InterviewFeedbackDTO.builder()
                                .pk(feedback.getId())
                                .videoId(video.getId())
                                .comment(feedback.getComment())
                                .nickname(feedback.getMember().getNickname())
                                .build());
            });

            videoList.add(VideoDTO.builder()
                    .pk(video.getId())
                    .videoName(video.getVideoName())
                    .url(video.getVideoURL())
                    .build());
        });

        List<ReplyDTO> replyList = new ArrayList<>();
        interview.getReplies().forEach(reply -> {
            replyList.add(ReplyDTO.builder()
                            .pk(reply.getId())
                            .content(reply.getContent())
                            .date(reply.getDate())
                            .nickname(reply.getReplyer().getNickname())
                            .build());
        });

        int cnt = interview.getVideos().stream().mapToInt(video -> video.getFeedbacks().size()).sum();
        InterviewBoardDTO dto = InterviewBoardDTO.builder()
                .pk(interview.getId())
                .writer(interview.getWriter().getNickname())
                .title(interview.getTitle())
                .text(interview.getText())
                .videoList(videoList)
                .feedbackList(feedbackList)
                .replyList(replyList)
                .fCnt(cnt)
                .build();

        return dto;
    }
}
