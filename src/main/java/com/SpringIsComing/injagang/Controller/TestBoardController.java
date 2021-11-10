package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.PageRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/boards")
public class TestBoardController {

    @GetMapping("/essay-list")
    public String essayTest(PageRequestDTO pageRequestDTO){
        log.info("==========TestBoardController==========");
        return "boards/essay-list";
    }

    @GetMapping("/interview-list")
    public String interviewTest(PageRequestDTO pageRequestDTO) {
        log.info("==========TestBoardController==========");
        return "boards/interview-list";
    }
}
