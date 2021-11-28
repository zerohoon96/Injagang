package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.EssayFeedbackInfoDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayFeedback;
import com.SpringIsComing.injagang.Entity.Member;

public interface FeedbackService {
    public Long storeFeedback(Member member, Essay essay, EssayFeedbackInfoDTO essayFeedbackInfoDTO);
    public EssayFeedback findById(Long essayFeedbackId);
}
