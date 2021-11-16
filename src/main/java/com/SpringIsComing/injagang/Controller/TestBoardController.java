package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.RegisterDTO;
import com.SpringIsComing.injagang.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
public class TestBoardController {

    private final MemberService memberService;

    @GetMapping("/essay-list")
    public String test(){
        log.info("==========TestBoardController==========");
        return "/boards/essay-list";
    }

    @GetMapping("/test")
    public String test2(){
        return "/mypage/login";
    }

    @GetMapping("/test2")
    public String test3(){
        return "/mypage/reg";
    }

    @GetMapping("/test3")
    public String test4(){
        return "/mypage/mypage";
    }

}
