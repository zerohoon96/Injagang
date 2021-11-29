package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.EssayFeedback;
import com.SpringIsComing.injagang.Entity.EssayFeedbackComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackCommentRepository extends JpaRepository<EssayFeedbackComment, Long> {
    List<EssayFeedbackComment> findByEssayFeedback(EssayFeedback essayFeedback);
}
