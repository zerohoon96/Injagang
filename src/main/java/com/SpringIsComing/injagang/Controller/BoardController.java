package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.PageRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/boards")
public class BoardController {

    @GetMapping("/essay-list")
    public String essayTest(PageRequestDTO pageRequestDTO, Model model){
        log.info("==========TestBoardController==========");
        return "boards/essay-list";
    }

    @GetMapping("/interview-list")
    public String interviewTest(PageRequestDTO pageRequestDTO, Model model) {
        log.info("==========TestBoardController==========");
        return "boards/interview-list";
    }
}
