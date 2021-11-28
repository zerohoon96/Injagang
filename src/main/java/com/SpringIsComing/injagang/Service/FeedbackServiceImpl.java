package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.EssayFeedbackCommentDTO;
import com.SpringIsComing.injagang.DTO.EssayFeedbackInfoDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayFeedback;
import com.SpringIsComing.injagang.Entity.EssayFeedbackComment;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.EssayFeedbackRepository;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    private final EssayFeedbackRepository essayFeedbackRepository;
    private final MemberRepository memberRepository;
    private final EssayRepository essayRepository;

    @Override
    public Long storeFeedback(Member member, Essay essay, EssayFeedbackInfoDTO essayFeedbackInfoDTO) {
        EssayFeedback feedback = EssayFeedback.builder()
                .member(member)
                .essay(essay)
                .content(essayFeedbackInfoDTO.getContent())
                .build();
        for (int i = 0; i < essayFeedbackInfoDTO.getEveryComment().size(); i++) { //한 문제씩 탐색
            for (EssayFeedbackCommentDTO eachFeedback : essayFeedbackInfoDTO.getEveryComment().get(i).getCommentList()) { //각 문제의 첨삭을 하나씩 탐색
                EssayFeedbackComment essayFeedbackComment = EssayFeedbackComment.builder() //새로운 첨삭 객체 생성
                        .num(i + 1)
                        .start(eachFeedback.getStart())
                        .end(eachFeedback.getEnd())
                        .content(eachFeedback.getComment())
                        .essayFeedback(feedback)
                        .build();
                feedback.getFeedbackComments().add(essayFeedbackComment); //첨삭 리스트에 추가
            }
        }
        return essayFeedbackRepository.save(feedback).getId();
    }

    @Override
    public EssayFeedback findById(Long essayFeedbackId) {
        return essayFeedbackRepository.findFeedbackById(essayFeedbackId);
    }

}