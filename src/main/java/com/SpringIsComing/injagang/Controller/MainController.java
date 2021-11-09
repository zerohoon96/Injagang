package com.SpringIsComing.injagang.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    /**
     * 마이페이지
     */
    @GetMapping("/mypage")
    public void myPage() {

    }

    /**
     * 마이페이지에서 수정 버튼 눌렀을때
     * */
    @GetMapping("/mypage/update/{memberId}")
    public void memberUpdateStart() {

    }

    /**
     * 회원 정보 수정 화면에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/mypage/update/{memberId}")
    public void memberUpdateEnd() {
        //입력 확인 후 객체 변경
    }

    /**
     * login 화면
     **/
    @GetMapping("/login")
    public void loginStart() {

    }

    /**
     * login 화면에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/login")
    public void loginEnd() {
        //입력 확인 후 마이페이지로 이동
    }

    /**
     * 회원가입 화면
     * */
    @GetMapping("/register")
    public void registerStart() {

    }

    /**
     * 회원가입에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/register")
    public void registerEnd() {
        //입력 확인 후 객체 추가
    }
}