package com.SpringIsComing.injagang.Entity.alarm;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@DiscriminatorValue("E")
@NoArgsConstructor
public class EssayAlarm extends Alarm {

    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ESSAY_ID")
    Essay essay;

    public EssayAlarm(Member member, String content, LocalDateTime date, String nickname, Essay essay) {
        super(member, content, date);
        this.nickname = nickname;
        this.essay = essay;

    }
}
