package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.EssayWriteDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.DTO.MypageEssayDTO;
import com.SpringIsComing.injagang.Entity.Member;

import java.util.ArrayList;
import java.util.List;

public interface EssayService {

    public Long storeEssay(Essay essay);
    public Essay findEssay(Long essayId) throws Exception;


    default EssayWriteDTO essayEntityToDto(Essay essay) {
        List<EssayContent> ec = essay.getContents();
        List<EssayContent> tData = new ArrayList<>();
        List<EssayContent> dData = new ArrayList<>();
        for (int i = 0; i < ec.size(); i++) {
            if (ec.get(i).isTemplate()) {
                tData.add(ec.get(i));
            } else {
                dData.add(ec.get(i));
            }
        }
        EssayWriteDTO dto = EssayWriteDTO.builder()
                .id(essay.getId())
                .essayTitle(essay.getEssayTitle())
                .templateTitle(essay.getTemplateTitle())
                .tc(tData)
                .dc(dData)
                .build();
        return dto;
    }
    
    List<Essay> findEssays(Member member);

    default MypageEssayDTO toMypageEssayDTO(Essay essay) {

        return MypageEssayDTO.builder()
                .id(essay.getId())
                .title(essay.getTitle())
                .feedbackNum(essay.getFeedbacks().size())
                .questionNum(essay.getContents().size())
                .createTime(essay.getDate())
                .build();
    }
}