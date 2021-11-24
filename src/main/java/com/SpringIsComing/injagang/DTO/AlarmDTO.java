package com.SpringIsComing.injagang.DTO;

import com.SpringIsComing.injagang.Entity.alarm.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmDTO implements Comparable<AlarmDTO> {

    private Long id;

    private Long contentId;

    private String content;

    private String nickname;

    private int type;

    private LocalDateTime createTime;


    @Override
    public int compareTo(AlarmDTO o) {

        if (this.createTime.isAfter(o.getCreateTime())) {
            return 1;
        }
        else return -1;
    }
}
