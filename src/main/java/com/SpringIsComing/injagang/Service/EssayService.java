package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.MypageEssayDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;

import java.time.format.DateTimeFormatter;
import java.util.List;

public interface EssayService {

    List<Essay> findEssays(Member member);

    Long save(Essay essay);

    default MypageEssayDTO toMypageEssayDTO(Essay essay) {

        MypageEssayDTO dto = MypageEssayDTO.builder()
                .id(essay.getId())
                .title(essay.getTitle())
                .essayTitle(essay.getEssayTitle())
                .feedbackNum(essay.getFeedbacks().size())
                .questionNum(essay.getContents().size())
                .createTime(essay.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();

        if(essay.getAccess() == null)
            dto.setAccess(0);
        else
            dto.setAccess(essay.getAccess());

        return dto;
    }

}
