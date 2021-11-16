package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Service.BoardService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/essay/board")
    public String essayBoard(PageRequestDTO pageRequestDTO, Model model){
        log.info("==========essayBoard==========");

        model.addAttribute("result", service.getEssayList(pageRequestDTO));
        return "boards/essay-list";
    }

    @GetMapping("/interview/board")
    public String interviewBoard(PageRequestDTO pageRequestDTO, Model model) {
        log.info("==========interviewBoard==========");

        model.addAttribute("result", service.getInterviewList(pageRequestDTO));
        return "boards/interview-list";
    }

    @GetMapping("/essay/board/{pk}")
    public String essayViewer(@PathVariable("pk") Long eb_pk,
                              @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("----------essayViewer----------");

        /* eb_pk로 해당 Essay 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        EssayBoardDTO dto = service.readEssayBoard(eb_pk);
        model.addAttribute("result", dto);

        return "boards/essay-read";
    }

    @GetMapping("/interview/board/{pk}")
    public String interviewViewer(@PathVariable("pk") Long ib_pk,
                                  @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("----------interviewViewer----------");

        /* ib_pk로 해당 Interview 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        InterviewBoardDTO dto = service.readInterviewBoard(ib_pk);
        model.addAttribute("result", dto);

        return "boards/interview-read";
    }
}
