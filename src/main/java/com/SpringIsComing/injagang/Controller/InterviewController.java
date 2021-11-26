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
    String interviewInit(Model model) {
        Map<String, Integer> essayMap = new HashMap<String, Integer>(); //예상 질문이 있는 자기소개서만 추가 (면접 설정을 할때 무조건 질문 개수가 1개 이상이 되도록..)
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
        int baseQuestion = Integer.parseInt(allParameters.get("baseQuestion").toString());
        int csQuestion = Integer.parseInt(allParameters.get("csQuestion").toString());
        int expectedQuestion = Integer.parseInt(allParameters.get("expectedQuestion").toString());
        int userAddQuestion;

        System.out.println("크기 : "+allParameters.size());
        System.out.println(allParameters);

        for(userAddQuestion=0;userAddQuestion<allParameters.size();userAddQuestion++){
            if(!allParameters.containsKey("question"+(userAddQuestion+1))) {
                break;
            }
        }

        System.out.println("인성질문 개수 : "+baseQuestion);
        System.out.println("전공질문 개수 : "+csQuestion);
        System.out.println("예상질문 개수 : "+expectedQuestion);
        System.out.println("추가질문 개수 : "+userAddQuestion);

        //1. 인성, cs 면접 질문 디비에서 랜덤으로 뽑아서 리스트에 추가

        //2. 예상 댓글 질문 디비에서 랜덤으로 뽑아서 리스트에 추가

        //3. 직접 추가 질문 디비에서 랜덤으로 뽑아서 리스트에 추가

        //4. Shuffle
        System.out.println("InterviewController.interviewSubmit");
        questionList.add("안녕하세요 국민여러분 홍준표에요.");
        questionList.add("무야호");
        model.addAttribute("questions",questionList);
        model.addAttribute("interviewName",allParameters.get("interviewName"));
        return "interview/test";
    }

    @GetMapping("/test")
    String test() {
        return "interview/muyaho";
    }

}
