package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.TemplateViewDTO;
import com.SpringIsComing.injagang.Service.InterviewQuestionService;
import com.SpringIsComing.injagang.Service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final TemplateService templateService;
    private final InterviewQuestionService interviewQuestionService;

    //관리자 권한이 있는 사람만 접속 가능
    @GetMapping("/admin")
    public String adminPage(@SessionAttribute("loginSession") String nickname, Model model) {
        List<TemplateViewDTO> dtoList = templateService.findTemplates();

        log.info("dtoList = {}", dtoList);
        model.addAttribute("templateList", dtoList);
        return "admin/main";
    }

    @GetMapping("/template/read")
    public String readTemplate(@RequestParam Long id, Model model) {
        TemplateViewDTO dto = templateService.findTemplate(id);

        log.info("dto = {}", dto);
        model.addAttribute("dto", dto);
        return "admin/template-read";
    }


    @GetMapping("/admin/question")
    public String question(Model model) {

        model.addAttribute("questions", interviewQuestionService.findAll());

        return "admin/fucking";
    }
}
