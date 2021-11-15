package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.EssayDTO;
import com.SpringIsComing.injagang.DTO.InterviewDTO;
import com.SpringIsComing.injagang.DTO.PageRequestDTO;
import com.SpringIsComing.injagang.DTO.PageResultDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.Entity.Interview;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {

    Long registerEssay(EssayDTO dto);

    Long registerInterview(InterviewDTO dto);

    //자소서 게시판의 한 페이지 가져오기
    PageResultDTO<EssayDTO, Essay> getEssayList(PageRequestDTO requestDTO);

    //면접 게시판의 한 페이지 가져오기
    PageResultDTO<InterviewDTO, Interview> getInterviewList(PageRequestDTO requestDTO);

    //자소서 게시판의 한 게시물 읽어오기
    EssayDTO readEssayBoard(Long id);

    //면접 게시판의 한 게시물 읽어오기
    InterviewDTO readInterviewBoard(Long id);

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
                .regDate(interview.getDate())
                .build();

        return dto;
    }
}
