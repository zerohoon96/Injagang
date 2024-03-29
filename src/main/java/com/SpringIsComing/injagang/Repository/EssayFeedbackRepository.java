package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayFeedback;
import com.SpringIsComing.injagang.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayFeedbackRepository extends JpaRepository<EssayFeedback, Long> {
    EssayFeedback findFeedbackById(Long essayFeedbackId);
    void deleteById(Long essayFeedbackId);
}
