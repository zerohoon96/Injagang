package com.SpringIsComing.injagang.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "interview/init";
    }

    /**
     * 모의 면접 보기 화면
     **/
    @PostMapping("/test") //이거 새로 생성한 면접 객체 PK로 바꿔야함.. (/{interviewId}) pathvariable로 밑에서 쓰면 될듯
    String interviewSubmit(@RequestParam Map<String, Object> allParameters,
                           Model model) {
        List<String> questionList = new ArrayList<>();
        //1. 인성, cs 면접 질문 디비에서 랜덤으로 뽑아서 리스트에 추가

        //2. 예상 댓글 질문 디비에서 랜덤으로 뽑아서 리스트에 추가

        //4. Shuffle
        for (Map.Entry<String, Object> entry : allParameters.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("InterviewController.interviewSubmit");
        questionList.add("그대 눈동자에 건배");
        questionList.add("우린 간부잖아..");
        questionList.add("나 너무 무서워.. 이러다가는 다 죽어!");
        questionList.add("그럼 자네가 날 속이고, 내 구슬을 가져간 건 말이 되고?");
        questionList.add("밖에 나와보니까.... 그 사람들 말이 다 맞더라고. 여기가 더 지옥이야.");
        model.addAttribute("questions",questionList);
        return "interview/test";
    }

    @GetMapping("/test")
    String test() {
        return "interview/muyaho";
    }

}
