package com.SpringIsComing.injagang.Entity.alarm;

import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@DiscriminatorValue("I")
@NoArgsConstructor
public class InterviewAlarm extends Alarm{

    private String nickname;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "INTERVIEW_ID")
    Interview interview;

    public InterviewAlarm(Member member, String content, LocalDateTime date, String nickname, Interview interview) {
        super(member, content, date);
        this.nickname = nickname;
        this.interview = interview;
    }
}
