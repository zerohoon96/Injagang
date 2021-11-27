package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.MockInterview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MockInterviewRepository extends JpaRepository<MockInterview, Long> {

    List<MockInterview> findMockInterviewsByMember(Member member);
}
