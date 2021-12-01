package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.MockInterviewDTO;
import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InterviewServiceImpl implements InterviewService{

    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;
    private final MockInterviewRepository mockInterviewRepository;
    private final InterviewQuestionRepository questionRepository;

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

    //모의면접 객체 저장
    @Override
    public void registerTestInterview(int qCnt, String interviewName, String nickname) {

        MockInterview mockInterview = MockInterview.builder()
                            .member(memberRepository.findByNickname(nickname))
                            .qCnt(qCnt)
                            .title(interviewName)
                            .date(LocalDateTime.now())
                            .build();

        mockInterviewRepository.save(mockInterview);
    }

    @Override
    public List<MockInterviewDTO> findMockInterviews(String nickname){
        Member member = memberRepository.findByNickname(nickname);
        List<MockInterview> mockInterview = mockInterviewRepository.findMockInterviewsByMember(member);

        List<MockInterviewDTO> dtoList = new ArrayList<>();
        mockInterview.forEach(mock -> {
            dtoList.add(MockInterviewDTO.builder()
                            .pk(mock.getId())
                            .nickname(mock.getMember().getNickname())
                            .title(mock.getTitle())
                            .qNum(mock.getQCnt())
                            .date(mock.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                            .build());
        });

        return dtoList;
    }

    @Override
    public void getRandomBasicQuestions(List<String> questionList, int baseQuestion, int csQuestion, int jobQuestion, int situationQuestion) {
        List<InterviewQuestion> allQuestions = questionRepository.findAll();

        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < allQuestions.size(); i++) {
            integerList.add(i);
        }

        Collections.shuffle(integerList);
        for (int i : integerList) {
            if(baseQuestion <= 0) break;

            if(allQuestions.get(i).getQuestionType() == QuestionType.PERSONALITY) {
                questionList.add(allQuestions.get(i).getTitle());
                baseQuestion--;
            }
        }

        Collections.shuffle(integerList);
        for (int i : integerList) {
            if(csQuestion <= 0) break;

            if(allQuestions.get(i).getQuestionType() == QuestionType.CS) {
                questionList.add(allQuestions.get(i).getTitle());
                csQuestion--;
            }
        }

        Collections.shuffle(integerList);
        for (int i : integerList) {
            if(jobQuestion <= 0) break;

            if(allQuestions.get(i).getQuestionType() == QuestionType.JOB) {
                questionList.add(allQuestions.get(i).getTitle());
                jobQuestion--;
            }
        }

        Collections.shuffle(integerList);
        for (int i : integerList) {
            if(situationQuestion <= 0) break;

            if(allQuestions.get(i).getQuestionType() == QuestionType.SITUATION) {
                questionList.add(allQuestions.get(i).getTitle());
                situationQuestion--;
            }
        }
    }

    @Override
    public List<Integer> getQuestionNums() {
        List<Integer> list = new ArrayList<>();

        list.add(questionRepository.countByQuestionType(QuestionType.PERSONALITY));
        list.add(questionRepository.countByQuestionType(QuestionType.CS));
        list.add(questionRepository.countByQuestionType(QuestionType.JOB));
        list.add(questionRepository.countByQuestionType(QuestionType.SITUATION));
        return list;
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