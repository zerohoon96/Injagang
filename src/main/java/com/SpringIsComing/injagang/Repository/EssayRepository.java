package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssayRepository extends JpaRepository<Essay, Long>, QuerydslPredicateExecutor<Essay> {

    @Query("select e from Essay e join fetch e.writer where e.writer = :m")
    List<Essay> findEssaysByMember(@Param("m") Member member);

}

