package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.EssayDTO;
import com.SpringIsComing.injagang.DTO.InterviewDTO;
import com.SpringIsComing.injagang.DTO.PageRequestDTO;
import com.SpringIsComing.injagang.DTO.PageResultDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Interview;

public interface BoardService {

    //자소서 게시판의 한 페이지 가져오기
    PageResultDTO<EssayDTO, Essay> getEssayList(PageRequestDTO requestDTO);

    //면접 게시판의 한 페이지 가져오기
    PageResultDTO<InterviewDTO, Interview> getInterviewList(PageRequestDTO requestDTO);
}
