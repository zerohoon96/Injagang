package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.EssayFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayFeedbackRepository extends JpaRepository<EssayFeedback, Long> {
}
