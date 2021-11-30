package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Entity.alarm.Alarm;
import com.SpringIsComing.injagang.Entity.alarm.EssayAlarm;
import com.SpringIsComing.injagang.Entity.alarm.FriendAlarm;
import com.SpringIsComing.injagang.Entity.alarm.InterviewAlarm;
import com.SpringIsComing.injagang.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmServiceImpl  implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final EssayRepository essayRepository;
    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;


    @Override
    public List<Alarm> findAlarmsByMember(Member member) {

        return alarmRepository.findAlarmsByMember(member);

    }

    @Override
    public List<EssayAlarm> findEssayAlarmsByMember(Member member) {
        return alarmRepository.findEssayAlarmsByMember(member);
    }

    @Override
    public List<InterviewAlarm> findInterviewAlarmsByMember(Member member) {
        return alarmRepository.findInterviewAlarmsByMember(member);
    }

    @Override
    public List<FriendAlarm> findFriendAlarmsByMember(Member member) {
        return alarmRepository.findFriendAlarmsByMember(member);
    }

    @Override
    public Long addEssayReplyAlarm(Long essayId, String nickname) {
        return createEssayAlarm(essayId, nickname,"댓글");
    }

    @Override
    public Long addFeedbackAlarm(Long essayId, String nickname) {
        return createEssayAlarm(essayId, nickname,"피드백");

    }

    @Override
    public Long addExpectedQuestionAlarm(Long essayId, String nickname) {
        return createEssayAlarm(essayId, nickname,"예상질문");
    }

    @Override
    public Long addProofreadAlarm(Long essayId, String nickname){
        return createEssayAlarm(essayId, nickname, "첨삭");
    }

    private Long createEssayAlarm(Long essayId, String nickname, String type) {
        Essay essay = essayRepository.findById(essayId).orElseThrow(
                () -> new IllegalArgumentException("에세이 없당")
        );

        if (essay.getWriter().getNickname().equals(nickname)) {
            return 0L;
        }

        String content;
        String title = essay.getTitle();


        content = nickname + " 님이 " + title + "에 " + type +"을 남겼습니다.";


        EssayAlarm essayAlarm = new EssayAlarm(essay.getWriter(), content, LocalDateTime.now(), nickname, essay);

        return alarmRepository.save(essayAlarm).getId();
    }

    @Override
    public Long addInterviewReplyAlarm(Long interviewId, String nickname) {

        return createInterviewAlarm(interviewId, nickname,"댓글");

    }

    @Override
    public Long addInterviewFeedbackAlarm(Long interviewId, String nickname) {

        return createInterviewAlarm(interviewId, nickname,"피드백");


    }

    private Long createInterviewAlarm(Long interviewId, String nickname, String type) {
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(
                () -> new IllegalArgumentException("인터뷰 없당")
        );


        if (interview.getWriter().getNickname().equals(nickname)) {
            return 0L;
        }

        String content;
        String title = interview.getTitle();

        content = nickname + " 님이 " + title + "에 " + type +"을 남겼습니다.";

        InterviewAlarm interviewAlarm = new InterviewAlarm(interview.getWriter(), content, LocalDateTime.now(), nickname, interview);

        return alarmRepository.save(interviewAlarm).getId();
    }

    @Override
    public Long addFriendRequestAlarm(String targetNickname, String loginNickname) {
        return createFriendAlarm(targetNickname, loginNickname, " 님이 친구신청을 했습니다.");

    }


    @Override
    public Long addFriendAcceptAlarm(String targetNickname, String loginNickname) {
        return createFriendAlarm(targetNickname, loginNickname, " 님이 친구신청을 수락했습니다.");
    }


    private Long createFriendAlarm(String targetNickname, String loginNickname, String type) {

        Member targetMember = memberRepository.findByNickname(targetNickname);

        String content;

        content = loginNickname + type;

        FriendAlarm friendAlarm = new FriendAlarm(targetMember, content, LocalDateTime.now(), loginNickname);

        return alarmRepository.save(friendAlarm).getId();
    }

    @Override
    public void alarmRead(Long alarmId) {

        Alarm findAlarm = alarmRepository.findById(alarmId).orElseThrow(
                () -> new IllegalArgumentException("알람이 없어용")
        );
        findAlarm.alarmRead();

    }

}
