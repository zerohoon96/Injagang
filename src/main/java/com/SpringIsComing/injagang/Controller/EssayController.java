package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/essay")
@RequiredArgsConstructor
public class EssayController {

    private final MemberService memberService;
    private final EssayService essayService;
    private final TemplateService templateService;
    private final FeedbackService feedbackService;
    private final AlarmService alarmService;
    private final FeedbackCommentService feedbackCommentService;
    private final Comparator<EssayFeedbackComment> comparator = new Comparator<EssayFeedbackComment>() { //첨삭 정렬
        @Override
        public int compare(EssayFeedbackComment commentA, EssayFeedbackComment commentB) {
            int diff = commentA.getNum() - commentB.getNum();
            if (diff == 0) {
                return commentA.getStart() - commentB.getStart();
            }
            return diff;
        }
    };

    /**
     * * 자소서 입력
     */
    @GetMapping("/write")
    String essayInit(@SessionAttribute("loginSession") String nickname, Model model) {
        TemplateDTO dto = templateService.readTemplate();
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
        Member member = essayService.findMember(nickname);
        Essay e = Essay.createEssay(essayTitle, templateTitle, ec, member);
        Long essayId = essayService.storeEssay(e);
        redirectAttributes.addAttribute("essayId", essayId);

        return "redirect:/essay/read/{essayId}";
    }


    @GetMapping("/read/{essayId}")
    String essayRead(@SessionAttribute("loginSession") String nickname,
                     @PathVariable Long essayId, Model model) throws Exception {
        EssayWriteDTO dto = essayService.readEssay(essayId);
        model.addAttribute("essayDTO", dto);
        model.addAttribute("loginNickname", nickname);
        return "essay/read";
    }

    @PostMapping("/delete/{essayId}")
    String essayDelete(@SessionAttribute("loginSession") String nickname,
                       @PathVariable Long essayId, RedirectAttributes redirectAttributes) {
        essayService.deleteEssay(essayId);
        redirectAttributes.addAttribute("nickname",nickname);
        log.info(nickname);
        return "redirect:/mypage/{nickname}";
    }

    @GetMapping("/feedback/{essayId}/write")
        //첨삭 쓰기를 눌렀을때
    String writeFeedback(Model model,
                         @SessionAttribute("loginSession") String nickname,
                         @PathVariable Long essayId,
                         @ModelAttribute("feedback") EssayFeedbackInfoDTO feedback) throws Exception {
        Essay essay = essayService.findEssay(essayId);
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
        model.addAttribute("loginNickname", nickname);
        return "feedback/write";
    }

    @PostMapping("/feedback/{essayId}/write")
        //첨삭 저장을 눌렀을때
    String addFeedback(Model model,
                       @SessionAttribute("loginSession") String nickname,
                       @PathVariable Long essayId,
                       @ModelAttribute("feedback") EssayFeedbackInfoDTO feedback) throws Exception {
        LocalDateTime time = LocalDateTime.now();

        //레포지토리에 피드백 객체 저장
        Member writer = memberService.findByNickname(nickname);
        Essay essay = essayService.findEssay(essayId);

        //feedback 저장
        Long feedbackId = feedbackService.storeFeedback(writer, essay, feedback);
        alarmService.addProofreadAlarm(essayId, nickname);
        return "redirect:/essay/feedback/read/" + feedbackId; //첨삭 읽기로 redirect
    }

    @GetMapping("/feedback/read/{feedbackId}")
        //첨삭 읽기를 눌렀을때
    String readFeedback(Model model,
                        @SessionAttribute("loginSession") String nickname,
                        @PathVariable Long feedbackId,
                        @ModelAttribute("feedback") EssayFeedbackReadDTO feedback) throws Exception {
        EssayFeedback essayFeedback = feedbackService.findById(feedbackId); //feedbackID로 essayFeedback 저장
        Essay essay = essayService.findEssay(essayFeedback.getEssay().getId()); //essay 저장
        List<EssayFeedbackComment> feedbackComment = feedbackCommentService.findById(feedbackId); //첨삭 목록 저장
        List<EssayContent> essayContents = essay.getContents(); //자기소개서 내용 저장
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        List<Integer> startList = new ArrayList<>();
        List<Integer> endList = new ArrayList<>();
        List<String> contentList = new ArrayList<>();

        String content = feedbackService.findById(feedbackId).getContent();

        for (EssayContent essayContent : essayContents) { //전달을 위한 질문, 답변 리스트 생성
            questions.add(essayContent.getQuestion());
            answers.add(essayContent.getAnswer());
        }

        feedbackComment.sort(comparator); //문제번호, 시작 인덱스에 따라 정렬
        System.out.println("***첨삭 목록***" + feedbackComment.size());

        for (EssayFeedbackComment comment : feedbackComment) { //DTO에 데이터 삽입
            numList.add(comment.getNum());
            startList.add(comment.getStart());
            endList.add(comment.getEnd());
            contentList.add(comment.getContent());
        }

        feedback.setQuestions(questions);
        feedback.setAnswers(answers);
        feedback.setNum(numList);
        feedback.setStart(startList);
        feedback.setEnd(endList);
        feedback.setComment(contentList);
        feedback.setContent(content);
        feedback.setEssayPostName(essay.getEssayTitle());
        feedback.setFeedbackWriter(essayFeedback.getMember().getNickname());
        feedback.setFeedbackId(feedbackId);
        feedback.setEssayId(essay.getId());
        feedback.setCurUserNickname(nickname);
        feedback.setWriteTime(essayFeedback.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        model.addAttribute("loginNickname", nickname);
        return "feedback/read";
    }

    @GetMapping("/feedback/delete/{feedbackId}") //첨삭 삭제를 눌렀을때
    String deleteFeedback(@PathVariable Long feedbackId) {
        Long essayId = feedbackService.findById(feedbackId).getEssay().getId();
        feedbackService.deleteById(feedbackId);
        return "redirect:/essay/board/" + essayId;
    }

    @GetMapping("/feedback/update/{feedbackId}")
        //첨삭 수정을 눌렀을때
    String updateFeedback(Model model,
                          @SessionAttribute("loginSession") String nickname,
                          @PathVariable Long feedbackId,
                          @ModelAttribute("readFeedback") EssayFeedbackReadDTO readFeedback,
                          @ModelAttribute("writeFeedback") EssayFeedbackInfoDTO writeFeedback) throws Exception {
        log.info("update controller!!!!!!!!!");
        EssayFeedback essayFeedback = feedbackService.findById(feedbackId); //feedbackID로 essayFeedback 저장
        Essay essay = essayService.findEssay(essayFeedback.getEssay().getId()); //essay 저장
        List<EssayFeedbackComment> feedbackComment = feedbackCommentService.findById(feedbackId); //첨삭 목록 저장
        List<EssayContent> essayContents = essay.getContents(); //자기소개서 내용 저장

        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        List<Integer> startList = new ArrayList<>();
        List<Integer> endList = new ArrayList<>();
        List<String> contentList = new ArrayList<>();

        String content = feedbackService.findById(feedbackId).getContent();

        for (EssayContent essayContent : essayContents) { //전달을 위한 질문, 답변 리스트 생성
            questions.add(essayContent.getQuestion());
            answers.add(essayContent.getAnswer());
        }

        feedbackComment.sort(comparator); //문제번호, 시작 인덱스에 따라 정렬

        for (EssayFeedbackComment comment : feedbackComment) { //DTO에 데이터 삽입
            numList.add(comment.getNum());
            startList.add(comment.getStart());
            endList.add(comment.getEnd());
            contentList.add(comment.getContent());
        }

        readFeedback.setQuestions(questions);
        readFeedback.setAnswers(answers);
        readFeedback.setNum(numList);
        readFeedback.setStart(startList);
        readFeedback.setEnd(endList);
        readFeedback.setComment(contentList);
        readFeedback.setContent(content);
        readFeedback.setEssayPostName(essay.getEssayTitle());
        readFeedback.setFeedbackWriter(essayFeedback.getMember().getNickname());
        readFeedback.setFeedbackId(feedbackId);
        readFeedback.setEssayId(essay.getId());
        readFeedback.setCurUserNickname(nickname);
        readFeedback.setWriteTime(essayFeedback.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        model.addAttribute("loginNickname", nickname);
        return "feedback/update";
    }

    @PostMapping("/feedback/update/{feedbackId}")
        //첨삭 업데이트 저장을 눌렀을때
    String updateFeedback(Model model,
                       @SessionAttribute("loginSession") String nickname,
                       @PathVariable Long feedbackId,
                       @ModelAttribute("feedback") EssayFeedbackInfoDTO feedback) {

        //레포지토리에 피드백 객체 저장
        Member writer = memberService.findByNickname(nickname);
        EssayFeedback originFeedback = feedbackService.findById(feedbackId);
        Essay essay = originFeedback.getEssay();

        //feedback 저장
        Long newFeedbackId = feedbackService.storeFeedback(writer, essay, feedback);

        //기존 feedback 삭제
        feedbackService.deleteById(feedbackId);

        return "redirect:/essay/feedback/read/" + newFeedbackId; //첨삭 읽기로 redirect
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

        templateService.storeEssayTemplate(EssayTemplate.createEssayTemplate(templateTitle, etc));


        return "redirect:/essay/add";
    }

}