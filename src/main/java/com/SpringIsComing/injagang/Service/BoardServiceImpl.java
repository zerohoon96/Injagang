package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.EssayDTO;
import com.SpringIsComing.injagang.DTO.InterviewDTO;
import com.SpringIsComing.injagang.DTO.PageRequestDTO;
import com.SpringIsComing.injagang.DTO.PageResultDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Interview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    @Override
    public PageResultDTO<EssayDTO, Essay> getEssayList(PageRequestDTO requestDTO) {

        //미구현
        return null;
    }

    @Override
    public PageResultDTO<InterviewDTO, Interview> getInterviewList(PageRequestDTO requestDTO) {

        //미구현
        return null;
    }
}
