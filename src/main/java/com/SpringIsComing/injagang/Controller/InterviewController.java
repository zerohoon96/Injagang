package com.SpringIsComing.injagang.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/interview")
public class InterviewController {
    /**
     * 면접 세팅 화면
     **/
    @GetMapping("/init")
    public String interviewInit() {
        return "interview/init";
    }
}
