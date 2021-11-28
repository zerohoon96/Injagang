package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.EssayFeedbackInfoDTO;
import com.SpringIsComing.injagang.DTO.EssayFeedbackQuestionDTO;
import com.SpringIsComing.injagang.DTO.EssayWriteDTO;
import com.SpringIsComing.injagang.DTO.TemplateDTO;
import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.TemplateRepository;
import com.SpringIsComing.injagang.Service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/essay")
@RequiredArgsConstructor
public class EssayController {

    private final MemberService memberService;
    private final TemplateServiceImpl ts;
    private final EssayServiceImpl es;
    private final EssayService essayService;
    private final FeedbackService feedbackService;
    private final FeedbackCommentService feedbackCommentService;
    /**
     * * 자소서 입력
     */
    @GetMapping("/write")
    String essayInit(@SessionAttribute("loginSession") String nickname, Model model) {
        TemplateDTO dto = ts.readTemplate();
        model.addAttribute("templateDTO", dto);
        model.addAttribute("loginNickname", nickname);

        return "essay/write";
    }

    @PostMapping("/write")
    String essayWrite(@SessionAttribute("loginSession") String nickname,
                      @RequestParam Map<String, String> allParameters, Model model,
                      RedirectAttributes redirectAttributes) {

        List<EssayContent> ec = new ArrayList<>();

        // 자소서 제목
        String essayTitle = "emptyTitle";
        // 선택한 템플릿 이름
        String templateTitle = "emptyTemplate";
        // 템플릿에 대한 질문, 답변
        LinkedHashMap<String, String> t_data = new LinkedHashMap<>();
        // 직접입력에 대한 질문, 답변
        LinkedHashMap<String, String> d_data = new LinkedHashMap<>();

        Iterator<String> tmp_iterator = allParameters.keySet().iterator();
        while (tmp_iterator.hasNext()) {
            String key = tmp_iterator.next();
            if (key.equals("titleName")) {
                essayTitle = allParameters.get(key);
            }
            //템플릿이 선택 되었을 경우
            else if (key.equals("template_select") && (!allParameters.get(key).equals("emptyTemplate"))) {
                templateTitle = allParameters.get(key);
            } else if (key.equals("template_select") && (allParameters.get(key).equals("emptyTemplate"))) {
                //템플릿이 선택 안되었을 경우 아무것도 안함
            }
            //템플릿에 대한 질문,대답
            else if (key.indexOf("t") == 0) {
                String d1 = allParameters.get(key);
                ec.add(EssayContent.createEssayContent(key.substring(1), d1, true));
            }
            //직접입력에 대한 질문, 대답
            else {
                String d1 = allParameters.get(key);
                key = tmp_iterator.next();
                String d2 = allParameters.get(key);
                ec.add(EssayContent.createEssayContent(d1, d2, false));
            }
        }


        //return으로 ID를 반환받고 redirect~~에 add하고 key:value로 저장하면 됨
        Member member = es.findMember(nickname);
        Essay e = Essay.createEssay(essayTitle, templateTitle, ec, member);
        Long essayId = es.storeEssay(e);
        redirectAttributes.addAttribute("essayId", essayId);

        return "redirect:/essay/read/{essayId}";
    }


    @GetMapping("/read/{essayId}")
    String essayRead(@SessionAttribute("loginSession") String nickname,
                     @PathVariable Long essayId, Model model) throws Exception {
        EssayWriteDTO dto = es.readEssay(essayId);
        model.addAttribute("essayDTO", dto);
        model.addAttribute("loginNickname", nickname);
        return "essay/read";
    }

    @GetMapping("/feedback/{essayId}/write")
        //첨삭 쓰기를 눌렀을때
    String writeFeedback(Model model,
                         @SessionAttribute("loginSession") String nickname,
                         @PathVariable Long essayId,
                         @ModelAttribute("feedback") EssayFeedbackInfoDTO feedback) {
        Essay essay = es.findEssay(essayId);
        List<EssayContent> essayContents = essay.getContents();
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        for (EssayContent essayContent : essayContents) {
            questions.add(essayContent.getQuestion());
            answers.add(essayContent.getAnswer());
        }
        model.addAttribute("questions", questions);
        model.addAttribute("answers", answers);
        model.addAttribute("essayPostName", essay.getEssayTitle());
        return "feedback/write";
    }

    @PostMapping("/feedback/{essayId}/write")
        //첨삭 저장을 눌렀을때
    String addFeedback(Model model,
                       @SessionAttribute("loginSession") String nickname,
                       @PathVariable Long essayId,
                       @ModelAttribute EssayFeedbackInfoDTO feedback) {
        //레포지토리에 피드백 객체 저장
        Member writer = memberService.findByNickname(nickname);
        Essay essay = es.findEssay(essayId);

        feedbackService.storeFeedback(writer, essay, feedback);

        return "redirect:/essay/feedback/" + essayId + "/read/" + '0'; //첨삭 읽기로 redirect
    }

    @GetMapping("/feedback/{essayId}/read/{feedbackId}")
    String readFeedback(Model model,
                        @SessionAttribute("loginSession") String nickname,
                        @PathVariable Long essayId,
                        @PathVariable Long feedbackId,
                        @ModelAttribute EssayFeedbackInfoDTO feedback) {
        Essay essay = es.findEssay(essayId); //essay 저장
        Member writer = memberService.findByNickname(nickname); //작성자 저장
        List<EssayFeedbackComment> feedbackComment = feedbackCommentService.findFeedbackComment(nickname, essayId, feedbackId); //첨삭 목록 저장
        List<EssayContent> essayContents = essay.getContents(); //자기소개서 내용 저장
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        String content = feedbackService.findFeedback(nickname, essayId).getContent();

        for (EssayContent essayContent : essayContents) { //전달을 위한 리스트
            questions.add(essayContent.getQuestion());
            answers.add(essayContent.getAnswer());
        }

        System.out.println("***첨삭 목록***");
        for (EssayFeedbackComment essayFeedbackComment : feedbackComment) { ///////////////////////여기서 DTO에 삽입!
            System.out.println("문제 번호: " + essayFeedbackComment.getNum());
            System.out.println("시작: " + essayFeedbackComment.getStart());
            System.out.println("끝: " + essayFeedbackComment.getEnd());
            System.out.println("내용: " + essayFeedbackComment.getContent());
            System.out.println("=========================");
        }
        feedback.setContent(content); //총평 저장
        model.addAttribute("questions", questions);
        model.addAttribute("answers", answers);
        model.addAttribute("essayPostName", "삼성 자소서 첨삭 해주세요!");
        return "feedback/read";
    }


    //템플릿 추가
    @GetMapping("/add")
    String addInit(@SessionAttribute("loginSession") String nickname, Model model) {
        model.addAttribute("loginNickname", nickname);
        return "essay/add";
    }

    //추가된 템플릿 repository에 저장
    @PostMapping("/add")
    String savaTemplate(@RequestParam Map<String, String> addInput) {

        List<EssayTemplateContent> etc = new ArrayList<>();
        String templateTitle = "";
        List<String> question = new ArrayList<>();
        Iterator<String> tmp_addInput = addInput.keySet().iterator();

        while (tmp_addInput.hasNext()) {
            String key = tmp_addInput.next();
            if (key.equals("inputTemplateTitle")) {
                templateTitle = addInput.get(key);
            } else {
                question.add(addInput.get(key));
                etc.add(EssayTemplateContent.createEssayTemplateContent(addInput.get(key)));
            }
        }

        ts.storeEssayTemplate(EssayTemplate.createEssayTemplate(templateTitle, etc));


        return "redirect:/essay/add";
    }

}