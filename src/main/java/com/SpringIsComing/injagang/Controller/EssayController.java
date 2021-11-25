package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.EssayFeedbackDTO;
import com.SpringIsComing.injagang.DTO.EssayWriteDTO;
import com.SpringIsComing.injagang.DTO.TemplateDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.Entity.EssayTemplate;
import com.SpringIsComing.injagang.Entity.EssayTemplateContent;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.TemplateRepository;
import com.SpringIsComing.injagang.Service.EssayService;
import com.SpringIsComing.injagang.Service.EssayServiceImpl;
import com.SpringIsComing.injagang.Service.TemplateService;
import com.SpringIsComing.injagang.Service.TemplateServiceImpl;
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

    private final EssayServiceImpl es;
    private final TemplateServiceImpl ts;

    /**
     ** 자소서 입력
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
        String essayTitle="emptyTitle";
        // 선택한 템플릿 이름
        String templateTitle="emptyTemplate";
        // 템플릿에 대한 질문, 답변
        LinkedHashMap<String, String> t_data = new LinkedHashMap<>();
        // 직접입력에 대한 질문, 답변
        LinkedHashMap<String, String> d_data = new LinkedHashMap<>();

        Iterator<String> tmp_iterator = allParameters.keySet().iterator();
        while(tmp_iterator.hasNext()) {
            String key = tmp_iterator.next();
            if (key.equals("titleName")) {
                essayTitle = allParameters.get(key);
            }
            //템플릿이 선택 되었을 경우
            else if (key.equals("template_select") && (!allParameters.get(key).equals("emptyTemplate"))) {
                templateTitle = allParameters.get(key);
            }
            else if (key.equals("template_select") && (allParameters.get(key).equals("emptyTemplate"))) {
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
        Essay e = Essay.createEssay(essayTitle, templateTitle, ec);
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

    @GetMapping("/feedback/{essayId}/write") //첨삭 쓰기를 눌렀을때
    String writeFeedback(Model model,
                         @SessionAttribute("loginSession") String nickname,
                         @PathVariable Long essayId,
                         @ModelAttribute("feedback") EssayFeedbackDTO feedback) {
        model.addAttribute("essayId", essayId); //아이디를 전달
        model.addAttribute("loginNickname", nickname);
        return "feedback/essay/write";
    }

//    @PostMapping("/feedback/{essayId}/write") //첨삭 저장을 눌렀을때
//    String addFeedback(Model model,
//                         @PathVariable Long essayId,
//                         @ModelAttribute("feedback") EssayFeedbackDTO feedback) {
//        //레포지토리에 피드백 객체 저장
//        feedback.setId(0L);
//        return "redirect:/feedback/" + feedback.getId(); //첨삭 읽기로 redirect
//    }

    @GetMapping("/feedback/{feedbackId}") //첨삭 읽기를 눌렀을때 zzzzzzzzzzzzzzzzzzzzzzzzz url 설정!!!! 쿼리 파라메터로 할까?
    String readFeedback(@SessionAttribute("loginSession") String nickname,
                        Model model, @RequestParam String feedbackId) {
        System.out.println(feedbackId); //id를 사용해서 DTO 첨삭을 담는 DTO 생성
        model.addAttribute("loginNickname", nickname);
        return "feedback/essay/read";
    }


    //템플릿 추가
    @GetMapping("/add")
    String addInit(@SessionAttribute("loginSession") String nickname,  Model model) {
        model.addAttribute("loginNickname", nickname);
        return "essay/add";
    }

    //추가된 템플릿 repository에 저장
    @PostMapping("/add")
    String savaTemplate(@RequestParam Map<String, String> addInput) {

        List<EssayTemplateContent> etc = new ArrayList<>();
        String templateTitle="";
        List<String> question = new ArrayList<>();
        Iterator<String> tmp_addInput = addInput.keySet().iterator();

        while(tmp_addInput.hasNext()) {
            String key = tmp_addInput.next();
            if (key.equals("inputTemplateTitle")) {
                templateTitle = addInput.get(key);
            }
            else {
                question.add(addInput.get(key));
                etc.add(EssayTemplateContent.createEssayTemplateContent(addInput.get(key)));
            }
        }

        ts.storeEssayTemplate(EssayTemplate.createEssayTemplate(templateTitle, etc));
        

        return "redirect:/essay/add";
    }

    @GetMapping("/dragtest")
    String dragInit() {
        return "essay/dragtest";
    }
}