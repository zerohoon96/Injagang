package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;


    @GetMapping("confirm-email")
    public String viewConfirmEmail(@Valid @RequestParam String token){
        memberService.confirmEmail(token);
        log.info("에라이");


        return "redirect:/login";
    }
}
