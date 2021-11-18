package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Service.BoardService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/essay/board")
    public String essayBoard(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model){
        log.info("==========essayBoard==========");

        model.addAttribute("result", service.getEssayList(pageRequestDTO));
        return "boards/essay-list";
    }

    @GetMapping("/interview/board")
    public String interviewBoard(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("==========interviewBoard==========");

        model.addAttribute("result", service.getInterviewList(pageRequestDTO));
        return "boards/interview-list";
    }

    @GetMapping("/essay/board/{pk}")
    public String essayViewer(@SessionAttribute("loginSession") String nickname,
                              @PathVariable("pk") Long eb_pk,
                              @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("----------essayViewer----------");

        /* eb_pk로 해당 Essay 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        EssayBoardDTO dto = service.readEssayBoard(eb_pk);
        model.addAttribute("result", dto);

        return "boards/essay-read";
    }

    @GetMapping("/interview/board/{pk}")
    public String interviewViewer(@SessionAttribute("loginSession") String nickname,
                                  @PathVariable("pk") Long ib_pk,
                                  @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("----------interviewViewer----------");

        /* ib_pk로 해당 Interview 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        InterviewBoardDTO dto = service.readInterviewBoard(ib_pk);
        model.addAttribute("result", dto);

        return "boards/interview-read";
    }

    @GetMapping("/essay/board/add")
    public String registerEssayBoard(@SessionAttribute("loginSession") String nickname, Model model) {
        log.info("----------registerEssayBoard----------");

        //현재 로그인 되어있는 nickname으로 그 사람이 쓴 자소서 리스트 불러오기
        model.addAttribute("essayList", service.getEssays(nickname));
        return "boards/essay-register";
    }

    @PostMapping("/essay/board/add")
    public String registerEssayBoardPost(@SessionAttribute("loginSession") String nickname,
                                         EssayDTO dto, RedirectAttributes redirectAttributes) {
        log.info("----------registerEssayBoardPost----------");

        log.info("dto: " + dto);
        service.registerEssay(dto);
        return "redirect:/essay/board";
    }

    @GetMapping("interview/board/add")
    public String registerInterviewBoard(@SessionAttribute("loginSession") String nickname, Model model) {
        log.info("----------registerInterviewBoardPost----------");

        model.addAttribute("interviewList", service.getInterviews(nickname));
        return "boards/interview-register";
    }

    @PostMapping("/interview/board/add")
    public String registerEssayInterviewPost(@SessionAttribute("loginSession") String nickname,
                                             InterviewDTO dto, RedirectAttributes redirectAttributes) {
        log.info("----------registerEssayBoardPost----------");

        //service.registerInterview(dto);
        return "redirect:/interview/board";
    }
}
