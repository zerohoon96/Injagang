package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.EssayFeedbackComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackCommentRepository extends JpaRepository<EssayFeedbackComment, Long> {
}
