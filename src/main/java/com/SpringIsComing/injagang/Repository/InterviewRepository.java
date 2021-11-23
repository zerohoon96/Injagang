package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>, QuerydslPredicateExecutor<Interview> {

    @Query("select i from Interview i join i.writer m on m = :m")
    List<Interview> findInterviewsByMember(@Param("m") Member member);

}
