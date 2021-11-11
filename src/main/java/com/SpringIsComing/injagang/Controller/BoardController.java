package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.PageRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping
public class BoardController {

    @GetMapping("/essay/board")
    public String essayBoard(PageRequestDTO pageRequestDTO, Model model){
        log.info("==========essayBoard==========");
        return "boards/essay-list";
    }

    @GetMapping("/interview/board")
    public String interviewBoard(PageRequestDTO pageRequestDTO, Model model) {
        log.info("==========interviewBoard==========");
        return "boards/interview-list";
    }

    @GetMapping("/essay/board/{eb_pk}")
    public String essayViewer(@PathVariable Integer eb_pk,
            PageRequestDTO pageRequestDTO, Model model) {
        log.info("----------essayViewer----------");

        /* eb_pk로 해당 Essay 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        return "boards/essay-read";
    }

    @GetMapping("/interview/board/{ib_pk}")
    public String interviewViewer(PageRequestDTO pageRequestDTO, Model model) {
        log.info("----------interviewViewer----------");

        /* ib_pk로 해당 Interview 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        return "boards/interview-read";
    }
}
