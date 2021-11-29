package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.Service.InterviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/interview")
@RequiredArgsConstructor
@Slf4j
public class InterviewController {

    private final InterviewService service;

    /**
     * 면접 세팅 화면
     **/
    @GetMapping("/init")
    String interviewInit(@SessionAttribute("loginSession") String nickname, Model model) {
        Map<String, Integer> essayMap = new HashMap<>(); //예상 질문이 있는 자기소개서만 추가 (면접 설정을 할때 무조건 질문 개수가 1개 이상이 되도록..)
//        essayMap.put("2021 하반기 삼성전자",5);
//        essayMap.put("2021 하반기 LG전자",6);
//        essayMap.put("2021 하반기 Kakao",3);
//        essayMap.put("2021 하반기 Naver",9);

        //예상질문이 달린 자소서들 가지고 오기
        service.getEssays(essayMap, nickname);
//        log.info("essayMap = {}", essayMap);

        model.addAttribute("baseQuestion",10); //인성면접 개수
        model.addAttribute("csQuestion",6); //전공면접 개수
        model.addAttribute("essayMap",essayMap); //자소서 이름, 질문수
        model.addAttribute("loginNickname", nickname);
        return "interview/init";
    }

    /**
     * 모의 면접 보기 화면
     **/
    @PostMapping("/test") //이거 새로 생성한 면접 객체 PK로 바꿔야함.. (/{interviewId}) pathvariable로 밑에서 쓰면 될듯
    String interviewSubmit(@SessionAttribute("loginSession") String nickname,
                           @RequestParam Map<String, Object> allParameters,
                           Model model) {
        List<String> questionList = new ArrayList<>();
        int baseQuestion = Integer.parseInt(allParameters.get("baseQuestion").toString());
        int csQuestion = Integer.parseInt(allParameters.get("csQuestion").toString());
//        int expectedQuestion = Integer.parseInt(allParameters.get("expectedQuestion").toString());
        int userAddQuestion;

        log.info("크기 : " + allParameters.size());
        log.info("" + allParameters);

        for(userAddQuestion=0;userAddQuestion<allParameters.size();userAddQuestion++){
            if(!allParameters.containsKey("question"+(userAddQuestion+1))) {
                break;
            }
            else{ //직접 추가한 질문들을 모두 리스트에 추가
                questionList.add(allParameters.get("question"+(userAddQuestion+1)).toString());
            }
        }

        // 인성, cs 면접 질문 해당 개수만큼 디비에서 랜덤으로 뽑아서 리스트에 추가


        // 예상 댓글 질문 해당 개수만큼 디비에서 랜덤으로 뽑아서 리스트에 추가
//        service.getRandomExpectedQuestions(questionList, expectedQuestion, nickname);

        // 질문 순서 섞기
        Collections.shuffle(questionList);

        log.info("questionList ={}", questionList);

        model.addAttribute("questions", questionList);
        model.addAttribute("interviewName", allParameters.get("interviewName"));
        model.addAttribute("loginNickname", nickname);
        return "interview/test";
    }

    @GetMapping("/test")
    String test(@SessionAttribute("loginSession") String nickname, Model model) {
        model.addAttribute("loginNickname", nickname);
        return "interview/muyaho";
    }

}
