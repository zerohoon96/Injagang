package com.SpringIsComing.injagang.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/interview")
public class InterviewController {
    /**
     * 면접 세팅 화면
     **/
    @GetMapping("/init")
    String interviewInit(Model model) {
        Map<String, Integer> essayMap = new HashMap<String, Integer>();
        essayMap.put("2021 하반기 삼성전자",5);
        essayMap.put("2021 하반기 LG전자",6);
        essayMap.put("2021 하반기 Kakao",3);
        essayMap.put("2021 하반기 Naver",9);

        model.addAttribute("baseQuestion",10); //인성면접 개수
        model.addAttribute("csQuestion",6); //전공면접 개수
        model.addAttribute("essayMap",essayMap); //자소서 이름, 질문수
        return "/interview/interview-init";
    }

    /**
     * 면접 세팅 화면
     **/
    @PostMapping("/submit")
    void interviewSubmit() {
    }

}
