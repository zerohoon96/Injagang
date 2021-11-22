package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{

    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;


    @Override
    public List<Interview> findInterviews(Member member) {

        return interviewRepository.findInterviewsByMember(member);
    }


}