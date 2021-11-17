package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.MypageEssayDTO;
import com.SpringIsComing.injagang.DTO.MypageFriendDTO;
import com.SpringIsComing.injagang.DTO.MypageInterviewDTO;
import com.SpringIsComing.injagang.DTO.RegisterDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Friend;
import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.FriendRepository;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import com.SpringIsComing.injagang.Service.EssayService;
import com.SpringIsComing.injagang.Service.FriendService;
import com.SpringIsComing.injagang.Service.InterviewService;
import com.SpringIsComing.injagang.Service.MemberService;
import com.SpringIsComing.injagang.session.SessionConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class MainControllerTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EssayService essayService;

    @Autowired
    EssayRepository essayRepository;

    @Autowired
    InterviewService interviewService;

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;

    @Test
    public void test() {
        RegisterDTO registerDTO1 = new RegisterDTO();
        registerDTO1.setEmail("aaa@naver.com");//자신의 이메일을 넣어서 테스트해보세욤
        registerDTO1.setName("황재환");
        registerDTO1.setLoginId("zxcv00691");
        registerDTO1.setNickname("test11");
        registerDTO1.setPassword("test11");
        registerDTO1.setPasswordCheck("test11");

        Long save = memberService.save(registerDTO1);


        RegisterDTO registerDTO2 = new RegisterDTO();
        registerDTO2.setEmail("zzz@naver.com");//자신의 이메일을 넣어서 테스트해보세욤
        registerDTO2.setName("황재환");
        registerDTO2.setLoginId("zxcv00692");
        registerDTO2.setNickname("test2");
        registerDTO2.setPassword("test");
        registerDTO2.setPasswordCheck("test");

        Long save2 = memberService.save(registerDTO2);


        Member findMember = memberRepository.findById(save).get();

        Essay essay = Essay.builder()
                .title("test1")
                .writer(findMember)
                .date(LocalDateTime.now())
                .build();
        essayRepository.save(essay);

        Interview interview = Interview.builder()
                .title("test2")
                .writer(findMember)
                .date(LocalDateTime.now())
                .build();
        interviewRepository.save(interview);


        Friend friend = Friend.builder()
                .memberA_id(save)
                .memberB_id(save2)
                .build();

        friendRepository.save(friend);

        Member loginMember = memberService.findByNickname("test11");
        Member targetMember = memberService.findByNickname("test2");

        boolean friendCheck = friendService.isFriend(loginMember.getId(), targetMember.getId());

        if (loginMember.getNickname() == targetMember.getNickname()) {
            friendCheck = true;
        }

        List<MypageEssayDTO> essayDTOS = essayService.findEssays(loginMember).stream()
                .map(e -> essayService.toMypageEssayDTO(e)).collect(Collectors.toList());
        List<MypageInterviewDTO> interviewDTOS = interviewService.findInterviews(loginMember).stream()
                .map(i -> interviewService.toMypageInterViewDTO(i)).collect(Collectors.toList());

        List<MypageFriendDTO> friendDTOS = friendService.findFriends(loginMember.getId()).stream()
                .map(f -> friendService.toMypageDTO(f, "쓰레기")).collect(Collectors.toList());


        System.out.println(essayDTOS);
        System.out.println(interviewDTOS);
        System.out.println(friendDTOS);

    }




}