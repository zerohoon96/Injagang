package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.AlarmDTO;
import com.SpringIsComing.injagang.DTO.AlarmIdDTO;
import com.SpringIsComing.injagang.Entity.alarm.Alarm;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.alarm.EssayAlarm;
import com.SpringIsComing.injagang.Service.AlarmService;
import com.SpringIsComing.injagang.Service.EssayService;
import com.SpringIsComing.injagang.Service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Controller
@RestController
@Slf4j
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;
    private final MemberService memberService;

    @GetMapping("/showAlarm")
    public List<AlarmDTO> test(@SessionAttribute("loginSession") String nickname) {

        log.info("자바스크립트 ㅈㄴ 맘에 안드네");

        Member loginMember = memberService.findByNickname(nickname);

        List<AlarmDTO> result = new ArrayList<>();


//        List<Alarm> alarmsByMember = alarmService.findAlarmsByMember(loginMember);

        List<AlarmDTO> essayDTO = alarmService.findEssayAlarmsByMember(loginMember).stream()
                .map(a -> alarmService.fromEssayAlarmToAlarmDTO(a)).collect(toList());

        List<AlarmDTO> interviewDTO = alarmService.findInterviewAlarmsByMember(loginMember).stream()
                .map(a -> alarmService.fromInterviewAlarmToAlarmDTO(a)).collect(toList());

        List<AlarmDTO> friendDTO = alarmService.findFriendAlarmsByMember(loginMember).stream()
                .map(a -> alarmService.fromFriendAlarmToAlarmDTO(a)).collect(toList());

        log.info("쓰벌={}",result.size());

        result.addAll(essayDTO);
        result.addAll(interviewDTO);
        result.addAll(friendDTO);

        Collections.sort(result);

        return result;

    }

    @PostMapping("/alarmRead")
    public String test2(@RequestBody AlarmIdDTO alarmIdDTO) {

        log.info("개시키야={}", alarmIdDTO.getId());
        Long id = alarmIdDTO.getId();
        alarmService.alarmRead(id);

        return "오카이";
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

}