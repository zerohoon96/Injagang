package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Service.BoardService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    //영상 업로드 위치
    @Value("${com.SpringIsComing.injagang.upload.path}")
    private String uploadPath;

    private final BoardService service;

    //자소서 게시판
    @GetMapping("/essay/board")
    public String essayBoard(@SessionAttribute("loginSession") String nickname,
                             @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model){
        log.info("==========essayBoard==========");

        model.addAttribute("result", service.getEssayList(pageRequestDTO));
        return "boards/essay-list";
    }

    //면접 게시판
    @GetMapping("/interview/board")
    public String interviewBoard(@SessionAttribute("loginSession") String nickname,
                                 @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("==========interviewBoard==========");

        model.addAttribute("result", service.getInterviewList(pageRequestDTO));
        return "boards/interview-list";
    }

    //자소서 게시물 보기
    @GetMapping("/essay/board/{pk}")
    public String essayViewer(@SessionAttribute("loginSession") String nickname,
                              @PathVariable("pk") Long eb_pk,
                              @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("----------essayViewer----------");

        /* eb_pk로 해당 Essay 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        EssayBoardDTO dto = service.readEssayBoard(eb_pk);
        model.addAttribute("result", dto);
        model.addAttribute("nickname", nickname);

        return "boards/essay-read";
    }

    //면접 게시물 보기
    @GetMapping("/interview/board/{pk}")
    public String interviewViewer(@SessionAttribute("loginSession") String nickname,
                                  @PathVariable("pk") Long ib_pk,
                                  @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("----------interviewViewer----------");

        /* ib_pk로 해당 Interview 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        InterviewBoardDTO dto = service.readInterviewBoard(ib_pk);
        model.addAttribute("result", dto);
        model.addAttribute("nickname", nickname);

        return "boards/interview-read";
    }

    //자소서 게시판 글쓰기 화면
    @GetMapping("/essay/board/add")
    public String registerEssayBoard(@SessionAttribute("loginSession") String nickname, Model model) {
        log.info("----------registerEssayBoard----------");

        //현재 로그인 되어있는 nickname으로 그 사람이 쓴 자소서 리스트 불러오기
        model.addAttribute("essayList", service.getEssays(nickname));
        return "boards/essay-register";
    }

    //자소서 글쓰기 완료 버튼 눌렀을 때
    @PostMapping("/essay/board/add")
    public String registerEssayBoardPost(@SessionAttribute("loginSession") String nickname,
                                         @ModelAttribute("dto") EssayDTO dto) {
        log.info("----------registerEssayBoardPost----------");

        /**
         * 게시물 등록하고 자소서 게시판으로 리다이렉트
         **/
        //log.info("dto: " + dto);
        service.registerEssay(dto);
        return "redirect:/essay/board";
    }

    //면접 게시판 글쓰기 화면
    @GetMapping("interview/board/add")
    public String registerInterviewBoard(@SessionAttribute("loginSession") String nickname, Model model) {
        log.info("----------registerInterviewBoard----------");

        model.addAttribute("interviewList", service.getInterviews(nickname));
        return "boards/interview-register";
    }

    //면접 글쓰기 완료 버튼 눌렀을 때
    @PostMapping("/interview/board/add")
    public String registerEssayInterviewPost(@SessionAttribute("loginSession") String nickname,
                                             @RequestParam("title") String title,
                                             @RequestParam("text") String text,
                                             @RequestParam("videoUrls") List<String> files) throws Exception{
        log.info("----------registerInterviewBoardPost----------");
        log.info("title: " + title + " text: " + text);

        List<String> srcPathList = new ArrayList<>();
        List<String> pathList = new ArrayList<>();
        for (String file : files) {
            String srcFileName = URLDecoder.decode(file, "UTF-8");
            String srcFilePath = uploadPath + File.separator + srcFileName;
            String filePath = "/upload/" + srcFileName;
            pathList.add(filePath);
            srcPathList.add(srcFilePath);
        }
        //log.info("srcPathList: " + srcPathList);
        log.info("pathList: " + pathList);

        InterviewDTO dto = InterviewDTO.builder()
                .title(title)
                .text(text)
                .videoUrls(pathList)
                .writer(nickname)
                .build();

        service.registerInterview(dto);
        return "redirect:/interview/board";
    }

    //자소서 게시물에서 예상질문 쓰기
    @PostMapping("/essay/question")
    public String registerEssayQuestion(@SessionAttribute("loginSession") String nickname,
                                        @RequestParam("essay_pk") Long essay_pk,
                                        @RequestParam("content_pk") Long content_pk,
                                        @RequestParam("questionContent")String content) {
        log.info("==========registerEssayQuestion==========");
        log.info("content: " + content);

        //예상질문 저장 후 리다이렉트
        service.registerExpectedQuestion(content_pk, content, nickname);
        return "redirect:/essay/board/" + essay_pk;
    }

    //자소서 게시물에서 댓글 쓰기
    @PostMapping("/essay/reply")
    public String registerEssayReply(@SessionAttribute("loginSession") String nickname,
                                     @RequestParam("essay_pk") Long essay_pk,
                                     @RequestParam("replyContent") String content) {
        log.info("==========registerEssayReply==========");
        log.info("content: " + content);

        service.registerEssayReply(essay_pk, content, nickname);
        return "redirect:/essay/board/" + essay_pk;
    }

    //면접 게시물에서 댓글 쓰기
    @PostMapping("/interview/reply")
    public String registerInterviewReply(@SessionAttribute("loginSession") String nickname,
                                         @RequestParam("pk") Long pk,
                                         @RequestParam("content") String content) {
        log.info("==========registerInterviewReply==========");
        log.info("content: " + content);

        service.registerInterviewReply(pk, content, nickname);
        return "redirect:/interview/board/" + pk;
    }
}
