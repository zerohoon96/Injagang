package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.EssayFeedback;
import com.SpringIsComing.injagang.Entity.EssayFeedbackComment;

import java.util.List;

public interface FeedbackCommentService {
    public List<EssayFeedbackComment> findFeedbackComment(String nickname, Long essayId, Long essayFeedbackId);
}
