package com.SpringIsComing.injagang.Entity.alarm;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@DiscriminatorValue("F")
@Entity
public class FriendAlarm extends Alarm{

    String nickname;

    public FriendAlarm(Member member, String content, LocalDateTime date, String nickname) {
        super(member, content,date);
        this.nickname = nickname;
    }
}
