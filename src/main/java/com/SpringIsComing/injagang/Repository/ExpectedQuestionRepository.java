package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.ExpectedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpectedQuestionRepository extends JpaRepository<ExpectedQuestion, Long> {
}
