package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.MypageEssayDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;

import java.util.List;

public interface EssayService {

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
