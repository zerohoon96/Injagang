package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.EssayFeedbackInfoDTO;
import com.SpringIsComing.injagang.DTO.EssayFeedbackQuestionDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.Service.EssayServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/essay")
@RequiredArgsConstructor
public class EssayController {

    private final EssayServiceImpl es;

    /**
     ** 자소서 입력
     */
    @GetMapping("/write")
    String essayInit() {
        return "essay/write";
    }

    @PostMapping("/write")
    String essayRead(@RequestParam Map<String, String> allParameters, Model model) {

        List<EssayContent> ec = new ArrayList<>();


        // 자소서 제목
        String name="";
        // 선택한 템플릿 이름
        String t_title="";
        // 템플릿에 대한 질문, 답변
        LinkedHashMap<String, String> t_data = new LinkedHashMap<>();
        // 직접입력에 대한 질문, 답변
        LinkedHashMap<String, String> d_data = new LinkedHashMap<>();
        System.out.println("allParameters = " + allParameters);
        Iterator<String> tmp_iterator = allParameters.keySet().iterator();
        while(tmp_iterator.hasNext()) {
            String key = tmp_iterator.next();
            if (key.equals("titleName")) {
                name += allParameters.get(key);
            }
            //템플릿이 선택 되었을 경우
            else if (key.equals("template_select") && (!allParameters.get(key).equals("t0"))) {
                t_title += allParameters.get(key);
            }
            //템플릿에 대한 질문,대답
            else if (key.indexOf("t") == 0) {
                String d1 = allParameters.get(key);
                ec.add(EssayContent.createEssayContent(key, d1, true));
                //                t_data.put(key, d1);
            }
            //직접입력에 대한 질문, 대답
            else {
                String d1 = allParameters.get(key);
                key = tmp_iterator.next();
                String d2 = allParameters.get(key);
                ec.add(EssayContent.createEssayContent(d1, d2, false));
                //                d_data.put(d1, d2);
            }
        }


        es.storeEssay(Essay.createEssay(name, t_title, ec));

//        System.out.println("name = " + name);
//        System.out.println("t_title = " + t_title);
//        System.out.println("t_data = " + t_data);
//        System.out.println("d_data = " + d_data);
        return "essay/read";
    }

    @GetMapping("/feedback/{essayId}/write") //첨삭 쓰기를 눌렀을때
    String writeFeedback(Model model,
                         @PathVariable Long essayId,
                         @ModelAttribute("feedback") EssayFeedbackInfoDTO feedback) {

        ////////////////////////////////////////////////////////////////////////////////// 테스트 자기소개서 추가.
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        questions.add("질문1 입니다. 지금 소감은요?");
        questions.add("질문2 입니다. 지금 보이는건 뭔가요?");
        questions.add("질문3 입니다. 아니 어쩌라고요");
        questions.add("질문4 입니다. 히히히히");
        questions.add("질문5 입니다. ㅋㅋㅋㅋㅋㅋㅋㅋ");

        answers.add("답변1 입니다. 집에 가고 싶군요 히히히히히히히히 키움 히어로즈 화이팅");
        answers.add("답변2 입니다. 제 앞에 휴지랑 두 개의 맥북이 있군요");
        answers.add("답변3 입니다. 할말이 없어요 그럼 이만");
        answers.add("답변4 입니다. 아니 뭐가 문젠가요?");
        answers.add("답변5 입니다. 훠훠훠 좌장면 좋아하세요?");
        //////////////////////////////////////////////////////////////////////////////////

        feedback.setQuestions(questions);
        feedback.setAnswers(answers);
        model.addAttribute("essayPostName","삼성 자소서 첨삭 해주세요!");
        return "feedback/essay/write";
    }

    @PostMapping("/feedback/{essayId}/write") //첨삭 저장을 눌렀을때
    String addFeedback(Model model,
                         @PathVariable Long essayId,
                         @ModelAttribute EssayFeedbackInfoDTO feedback) {
        //레포지토리에 피드백 객체 저장
        System.out.println("essayId : "+essayId);
//        System.out.println(feedback);
        for(int i =0;i<feedback.getEveryComment().size();i++) {
            System.out.println("================================================");
            System.out.println(i+1);
            System.out.println(feedback.getEveryComment().get(i));
        }
//        for (EssayFeedbackQuestionDTO data: feedback.getEveryComment()) {
//            System.out.println(data);
//        }
//        System.out.println(feedback.getEveryComment());
        return "feedback/essay/z";
//        feedback.setId(0L);
//        return "redirect:/feedback/" + feedback.getId(); //첨삭 읽기로 redirect
    }

    @GetMapping("/feedback/{feedbackId}")
    String readFeedback(Model model, @RequestParam String feedbackId) {
        System.out.println(feedbackId); //id를 사용해서 DTO 첨삭을 담는 DTO 생성
        return "feedback/essay/read";
    }

    @GetMapping("/test")
    String z(Model model) {
        return "essay/tmp";
    }
    @PostMapping("/test")
    String zz(Model model) {
        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        return "feedback/essay/z";
    }
}