package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{

    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;
    private final EssayRepository essayRepository;


    @Override
    public List<Interview> findInterviews(Member member) {

        return interviewRepository.findInterviewsByMember(member);
    }

    //예상질문이 달린 자소서 저장
    @Override
    public void getEssays(Map<String, Integer> essayMap, String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        List<Essay> essayList = member.getEssays();

        essayList.forEach(essay -> {
            List<EssayContent> contentList = essay.getContents();
            int cnt = contentList.stream().mapToInt(content -> content.getQuestions().size()).sum();

            if(cnt != 0){
                essayMap.put(essay.getEssayTitle(), cnt);
            }
        });
    }

    @Override
    public void getRandomExpectedQuestions(List<String> questionList, int cnt, String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        List<Essay> essayList = member.getEssays();
        List<ExpectedQuestion> allQuestions = new ArrayList<>();

        essayList.forEach(essay -> {
            List<EssayContent> contentList = essay.getContents();
            contentList.forEach(content -> {
                List<ExpectedQuestion> questions = content.getQuestions();
                questions.forEach(question -> {
                    allQuestions.add(question);
                });
            });
        });

        //랜덤하게 리스트에 집어넣기
        if (cnt == allQuestions.size()) {
            allQuestions.forEach(q -> {
                questionList.add(q.getText());
            });
        } else {
            List<Integer> integerList = new ArrayList<>();
            for (int i = 0; i < allQuestions.size(); i++) {
                integerList.add(i);
            }

            Collections.shuffle(integerList);
            for (int i : integerList) {
                if(cnt <= 0) break;

                questionList.add(allQuestions.get(i).getText());
                cnt--;
            }
        }
    }

}