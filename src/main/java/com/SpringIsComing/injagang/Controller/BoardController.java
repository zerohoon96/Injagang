package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Service.AlarmService;
import com.SpringIsComing.injagang.Service.BoardService;
import com.SpringIsComing.injagang.upload.UploadConst;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    //영상 업로드 위치
//    @Value("${com.SpringIsComing.injagang.upload.path}")
    private String uploadPath = UploadConst.UPLOAD_PATH;

    private final BoardService service;
    private final AlarmService alarmService;

    //자소서 게시판
    @GetMapping("/essay/board")
    public String essayBoard(@SessionAttribute(value = "loginSession",required = false) String nickname,
                             @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model){


        model.addAttribute("result", service.getEssayList(pageRequestDTO));
        model.addAttribute("loginNickname", nickname);
        return "boards/essay-list";
    }

    //면접 게시판
    @GetMapping("/interview/board")
    public String interviewBoard(@SessionAttribute(value = "loginSession",required = false) String nickname,
                                 @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model) {


        model.addAttribute("result", service.getInterviewList(pageRequestDTO));
        model.addAttribute("loginNickname", nickname);
        return "boards/interview-list";
    }

    //자소서 게시물 보기
    @GetMapping("/essay/board/{pk}")
    public String essayViewer(@SessionAttribute("loginSession") String nickname,
                              @PathVariable("pk") Long eb_pk,
                              @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {


        /* eb_pk로 해당 Essay 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        EssayBoardDTO dto = service.readEssayBoard(eb_pk);
        model.addAttribute("result", dto);
        model.addAttribute("loginNickname", nickname);

        return "boards/essay-read";
    }

    //면접 게시물 보기
    @GetMapping("/interview/board/{pk}")
    public String interviewViewer(@SessionAttribute("loginSession") String nickname,
                                  @PathVariable("pk") Long ib_pk,
                                  @RequestParam(required = false) boolean isNoVideo,
                                  @RequestParam(required = false) boolean title,
                                  @RequestParam(required = false) boolean text,
                                  @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {


        /* ib_pk로 해당 Interview 객체를 찾아 DTO로 변환 후 model attribute 추가하여 넘기기 */
        InterviewBoardDTO dto = service.readInterviewBoard(ib_pk);
        model.addAttribute("result", dto);
        model.addAttribute("loginNickname", nickname);

        return "boards/interview-read";
    }

    //자소서 게시물의 제목, 글 수정 화면
    @GetMapping("/essay/board/update/{pk}")
    public String updateEssayBoard(@SessionAttribute("loginSession") String nickname,
                                   @PathVariable("pk") Long eb_pk, Model model) {


        EssayBoardDTO dto = service.readEssayBoard(eb_pk);
        model.addAttribute("result", dto);
        model.addAttribute("loginNickname", nickname);

        return "boards/essay-update";
    }

    @PostMapping("/essay/board/update/{pk}")
    public String updateEssayBoardPost(@SessionAttribute("loginSession") String nickname,
                                       @PathVariable("pk") Long eb_pk,
                                       @RequestParam("title") String title,
                                       @RequestParam("text") String text) {



        service.updateEssayBoard(eb_pk, title, text);

        return "redirect:/essay/board/" + eb_pk;
    }

    //면접 게시물의 제목, 글 수정 화면
    @GetMapping("/interview/board/update/{pk}")
    public String updateInterviewBoard(@SessionAttribute("loginSession") String nickname,
                                       @PathVariable("pk") Long ib_pk, Model model) {


        InterviewBoardDTO dto = service.readInterviewBoard(ib_pk);
        model.addAttribute("result", dto);
        model.addAttribute("loginNickname", nickname);

        return "boards/interview-update";
    }

    @PostMapping("/interview/board/update/{pk}")
    public String updateInterviewBoardPost(@SessionAttribute("loginSession") String nickname,
                                           @PathVariable("pk") Long ib_pk,
                                           @RequestParam String title,
                                           @RequestParam String text,
                                           @RequestParam List<String> videoNames) {


        service.updateInterviewBoard(ib_pk, title, text, videoNames);

        return "redirect:/interview/board/" + ib_pk;
    }

    //자소서 게시물 삭제 - 객체를 삭제하지는 않음
    @GetMapping("/essay/board/delete/{pk}")
    public String deleteEssayBoard(@PathVariable Long pk) {

        service.deleteEssayBoard(pk);
        return "redirect:/essay/board";
    }

    //면접 게시물 삭제 - 객체를 삭제
    @GetMapping("/interview/board/delete/{pk}")
    public String deleteInterviewBoard(@PathVariable Long pk) {


        service.deleteInterviewBoard(pk);
        return "redirect:/interview/board";
    }

    //자소서 게시판 글쓰기 화면
    @GetMapping("/essay/board/add")
    public String registerEssayBoard(@SessionAttribute("loginSession") String nickname, Model model,
                                     @RequestParam(required = false) boolean isNoEssay,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) String text) {


        //현재 로그인 되어있는 nickname으로 그 사람이 쓴 자소서 리스트 불러오기
        List<EssayDTO> dtoList = service.getEssays(nickname);

        model.addAttribute("isNoEssay", isNoEssay);
        model.addAttribute("title", title);
        model.addAttribute("text", text);
        model.addAttribute("essayList", dtoList);
        model.addAttribute("loginNickname", nickname);
        return "boards/essay-register";
    }

    //자소서 글쓰기 완료 버튼 눌렀을 때
    @PostMapping("/essay/board/add")
    public String registerEssayBoardPost(@SessionAttribute("loginSession") String nickname,
                                         @ModelAttribute("dto") EssayDTO dto,
                                         RedirectAttributes redirectAttributes) {


        /**
         * 게시물 등록하고 자소서 게시판으로 리다이렉트
         **/


        if(dto.getPk() == -1){

            redirectAttributes.addAttribute("isNoEssay", true);
            redirectAttributes.addAttribute("title", dto.getTitle());
            redirectAttributes.addAttribute("text", dto.getText());
            return "redirect:/essay/board/add";
        }
        service.registerEssay(dto);
        return "redirect:/essay/board";
    }

    //면접 게시판 글쓰기 화면
    @GetMapping("interview/board/add")
    public String registerInterviewBoard(@SessionAttribute("loginSession") String nickname, Model model,
                                         @RequestParam(required = false) boolean isNoVideo,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) String text) {

        model.addAttribute("isNoVideo", isNoVideo);
        model.addAttribute("title", title);
        model.addAttribute("text", text);
        model.addAttribute("loginNickname", nickname);
        return "boards/interview-register";
    }

    //면접 글쓰기 완료 버튼 눌렀을 때
    @PostMapping("/interview/board/add")
    public String registerEssayInterviewPost(@SessionAttribute("loginSession") String nickname,
                                             @RequestParam("title") String title,
                                             @RequestParam("text") String text,
                                             @RequestParam(value = "videoNames", required = false) List<String> videoNames,
                                             @RequestParam("videoUrls") List<String> files,
                                             RedirectAttributes redirectAttributes) throws Exception{

        //영상을 한개도 업로드 안하고 올릴 시 예외 처리
        if (videoNames == null) {
            redirectAttributes.addAttribute("isNoVideo", true);
            redirectAttributes.addAttribute("title", title);
            redirectAttributes.addAttribute("text", text);
            return "redirect:/interview/board/add";
        }

        if (videoNames.size() != files.size()) {
            log.info("--------------------WARNING: video names and urls don't match--------------------");
        }

        //List<String> srcPathList = new ArrayList<>();
        List<String> pathList = new ArrayList<>();
        for (String file : files) {
            String srcFileName = URLDecoder.decode(file, "UTF-8");
            String srcFilePath = uploadPath + File.separator + srcFileName;
            String filePath = "/upload/" + srcFileName;
            pathList.add(filePath);
            //srcPathList.add(srcFilePath);
        }
        log.info("pathList: " + pathList);

        InterviewDTO dto = InterviewDTO.builder()
                .title(title)
                .text(text)
                .videoUrls(pathList)
                .videoNames(videoNames)
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

        //예상질문 저장 후 리다이렉트
        service.registerExpectedQuestion(content_pk, content, nickname);
        return "redirect:/essay/board/" + essay_pk;
    }

    //자소서 게시물에서 댓글 쓰기
    @PostMapping("/essay/reply")
    public String registerEssayReply(@SessionAttribute("loginSession") String nickname,
                                     @RequestParam("essay_pk") Long essay_pk,
                                     @RequestParam("replyContent") String content) {

        service.registerEssayReply(essay_pk, content, nickname);
        alarmService.addEssayReplyAlarm(essay_pk, nickname);
        return "redirect:/essay/board/" + essay_pk;
    }

    //면접 게시물에서 댓글 쓰기
    @PostMapping("/interview/reply")
    public String registerInterviewReply(@SessionAttribute("loginSession") String nickname,
                                         @RequestParam("pk") Long pk,
                                         @RequestParam("content") String content) {

        service.registerInterviewReply(pk, content, nickname);
        alarmService.addInterviewReplyAlarm(pk, nickname);
        return "redirect:/interview/board/" + pk;
    }

    //면접 게시물에서 피드백 쓰기
    @PostMapping("/interview/feedback")
    public String registerInterviewFeedbackPost(@SessionAttribute("loginSession") String nickname,
                                        @RequestParam("interview_pk") Long interview_pk,
                                        @RequestParam("video_pk") Long video_pk,
                                        @RequestParam("feedbackContent")String content) {

        service.registerInterviewFeedback(video_pk, content, nickname);
        alarmService.addInterviewFeedbackAlarm(interview_pk, nickname);
        return "redirect:/interview/board/" + interview_pk;
    }

    //자소서 게시물에서 자기가 쓴 예상질문 or 자기 게시물에 달린 예상질문 삭제하기
    @PostMapping("/essay/question/delete")
    public String deleteEssayBoardQuestion(@SessionAttribute("loginSession") String nickname,
                                      @RequestParam Long eb_pk, @RequestParam Long question_pk) {


        service.deleteEssayQuestion(question_pk);
        return "redirect:/essay/board/" + eb_pk;
    }

    //자소서 게시물에서 자기가 쓴 댓글 or 자기 게시물에 달린 댓글 삭제하기
    @PostMapping("/essay/reply/delete")
    public String deleteEssayBoardReply(@SessionAttribute("loginSession") String nickname,
                                   @RequestParam Long eb_pk, @RequestParam Long reply_pk) {

        service.deleteBoardReply(reply_pk);
        return "redirect:/essay/board/" + eb_pk;
    }

    //면접 게시물에서 자기가 쓴 댓글 or 자기 게시물에 달린 댓글 삭제하기
    @PostMapping("interview/reply/delete")
    public String deleteInterviewBoardReply(@SessionAttribute("loginSession") String nickname,
                                       @RequestParam Long ib_pk, @RequestParam Long reply_pk) {


        service.deleteBoardReply(reply_pk);
        return "redirect:/interview/board/" + ib_pk;
    }

    //면접 게시물에서 자기가 쓴 피드백 or 자기 게시물에 달린 피드백 삭제하기
    @PostMapping("interview/feedback/delete")
    public String deleteInterviewBoardFeedback(@SessionAttribute("loginSession") String nickname,
                                               @RequestParam Long ib_pk, @RequestParam Long feedback_pk) {


        service.deleteInterviewFeedback(feedback_pk);
        return "redirect:/interview/board/" + ib_pk;
    }
}
