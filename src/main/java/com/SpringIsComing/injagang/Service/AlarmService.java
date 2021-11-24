package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.AlarmDTO;
import com.SpringIsComing.injagang.DTO.UpdateDTO;
import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Entity.alarm.Alarm;
import com.SpringIsComing.injagang.Entity.alarm.EssayAlarm;
import com.SpringIsComing.injagang.Entity.alarm.FriendAlarm;
import com.SpringIsComing.injagang.Entity.alarm.InterviewAlarm;

import java.util.List;

public interface AlarmService {

    List<Alarm> findAlarmsByMember(Member member);
    List<EssayAlarm> findEssayAlarmsByMember(Member member);
    List<InterviewAlarm> findInterviewAlarmsByMember(Member member);
    List<FriendAlarm> findFriendAlarmsByMember(Member member);

    Long addEssayReplyAlarm(Long essayId,String nickname);
    Long addFeedbackAlarm(Long essayId,String nickname);
    Long addInterviewReplyAlarm(Long interviewId,String nickname);
    Long addInterviewFeedbackAlarm(Long interviewId,String nickname);
    Long addFriendRequestAlarm(String targetNickname, String loginNickname);
    Long addFriendAcceptAlarm(String targetNickname, String loginNickname);

    void alarmRead(Long alarmId);



    default AlarmDTO fromEssayAlarmToAlarmDTO(EssayAlarm essayAlarm) {

        return AlarmDTO.builder()
                .id(essayAlarm.getId())
                .contentId(essayAlarm.getEssay().getId())
                .content(essayAlarm.getContent())
                .nickname(essayAlarm.getNickname())
                .type(1)
                .createTime(essayAlarm.getDate())
                .build();

    }

    default AlarmDTO fromInterviewAlarmToAlarmDTO(InterviewAlarm interviewAlarm) {

        return AlarmDTO.builder()
                .id(interviewAlarm.getId())
                .contentId(interviewAlarm.getInterview().getId())
                .content(interviewAlarm.getContent())
                .nickname(interviewAlarm.getNickname())
                .type(2)
                .createTime(interviewAlarm.getDate())
                .build();

    }

    default AlarmDTO fromFriendAlarmToAlarmDTO(FriendAlarm friendAlarm) {

        return AlarmDTO.builder()
                .id(friendAlarm.getId())
                .contentId(null)
                .content(friendAlarm.getContent())
                .nickname(friendAlarm.getNickname())
                .type(3)
                .createTime(friendAlarm.getDate())
                .build();

    }


}
