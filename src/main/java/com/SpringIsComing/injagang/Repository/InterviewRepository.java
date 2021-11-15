package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
