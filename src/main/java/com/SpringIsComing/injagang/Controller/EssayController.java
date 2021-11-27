package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.EssayFeedbackInfoDTO;
import com.SpringIsComing.injagang.DTO.EssayFeedbackQuestionDTO;
import com.SpringIsComing.injagang.DTO.EssayWriteDTO;
import com.SpringIsComing.injagang.DTO.TemplateDTO;
import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.TemplateRepository;
import com.SpringIsComing.injagang.Service.*;
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
    private final FeedbackService feedbackService;
    private final MemberService memberService;
    private final EssayService essayService;

    /**
     * * 자소서 입력
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
        Member member = es.findMember(nickname);
        Essay e = Essay.createEssay(essayTitle, templateTitle, ec, member);
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

    @GetMapping("/feedback/{essayId}/write")
        //첨삭 쓰기를 눌렀을때
    String writeFeedback(Model model,
                         @SessionAttribute("loginSession") String nickname,
                         @PathVariable Long essayId,
                         @ModelAttribute("feedback") EssayFeedbackInfoDTO feedback) {

        ////////////////////////////////////////////////////////////////////////////////// 테스트 자기소개서 추가.
//        List<String> questions = new ArrayList<>();
//        List<String> answers = new ArrayList<>();
//        questions.add("질문1 입니다. 지금 소감은요?");
//        questions.add("질문2 입니다. 지금 보이는건 뭔가요?");
//        questions.add("질문3 입니다. 아니 어쩌라고요");
//        questions.add("질문4 입니다. 히히히히");
//        questions.add("질문5 입니다. ㅋㅋㅋㅋㅋㅋㅋㅋ");
//
//        answers.add("숭실대학교는 일제강점기 학생운동에서 평양 지역 항일운동의 중심이 되어 105인사건, 국민회사건, 3·1운동, 광주학생운동, 신사참배 거부운동 등에서 투철하고 일관된 민족정신을 발휘하였고, 서북지방 인재 교육의 산실 역할을 하였다.\n" +
//                "\n" +
//                "또한, 문화적 암흑기이던 당시에 전도 활동을 비롯하여 음악·체육·문예활동 등에서 선도적 역할을 하였으며, 특히 숭실축구단이 주축이 되어 1929년부터 서울팀과 벌인 경평축구전은 많은 국민을 열광하게 만들었다.현재 60여 개의 동아리가 활동하고 있고, 운동부로 축구부를 육성하고 있다. 교내 행사로 10월 숭실축전에서는 동문간담회·체육대회·학술활동·예술행사 등이 진행된다.");
//        answers.add("숭실대학(1954 ~ 1971) 일본 제국이 패망한 뒤, 숭실의 재건이 논의되었지만 당장은 이루어지지 못하고 있었다. 한국 전쟁 직후인 1953년에 영락교회가 숭실대학재건기성회와 숭실대학재단이사회를 조직하고 문교부에 '숭실대학 설립인가'를 요청했다. 1954년에 설립이 허가되면서 자진 폐교 16년만에 평양 숭실학당은 대학기관으로 승격하여 서울 숭실대학교로 재건되었다. 숭실대학은 영락교회의 임시건물을 임시 캠퍼스로 사용하다가, 1957년에 상도캠퍼스의 완성으로 이전하게 되었다. 1967년 한국기독교박물관을 개관하고 1969년 컴퓨터라는 용어마저 생소하던 때 대한민국 최초로 컴퓨터공학 교육을 실시했다.");
//        answers.add("숭전대학교(1971 ~ 1987) 1971년 대전대학(현재의 한남대학교)과 통합하면서 '숭전대학교'(崇田大學校, 숭실대학 + 대전대학)로 교명이 바뀌었고, 그 해 12월에 종합대학교로 승격되었다. 그 후 두 개의 대학으로 독립 운영하는 것이 바람직하다는 판단 아래 1982년 10월 재단이사회에서 두 대학의 분리를 정식으로 결의하여 문교부에 승인 요청을 하였으며 1983년 3월 새학기를 맞아 정식으로 분리 운용하게 된다.");
//        answers.add("숭실대학교(1987 ~ 현재) 1987년, 개교 90주년을 계기로 교명을 숭전대학교에서 숭실대학교로 환원했다.[8] 그리고 1997년, 개교 100주년을 맞이하면서 더 큰 변화와 발전을 위해 나아가고 있다. 2005년부터 본격화된 '캠퍼스 마스터플랜'에 따라 형남공학관, 조만식기념관, 웨스트민스터홀, 레지던스홀, 학생회관, 전산센터, 교육문화복지센터 등의 쾌적하고 혁신적인 교육환경을 갖추기 시작했다.\n" +
//                "\n" +
//                "훌륭한 역사와 전통의 토대 위에 민족의 대학으로 성장한 숭실대학교는 21세기에 필요한, 21세기를 이끌어갈 수 있는, 21세기를 살아갈 수 있는 인성과 전문성, 그리고 교양적인 기능을 겸비한 지식인인 숭실다움을 겸비한 인재의 양성과 함께 최초의 대학에서 최고의 대학으로 도약하기 위한 숭실 2020비전을 추진 중에 있다.");
//        answers.add("교양선택 수강학점은 20학점 이상에서 자유롭게 수강할 수 있다. 교양선택 영역은 12개 영역(문학과 예술, 역사와 철학, 정보와 기술, 봉사의 리더십, 창의성과 의사소통능력, 세계의 언어, 세계의 문화와 국제관계, 인간과 사회, 정치와 경제, 자연과학과 수리, 생활과 건강, 학문과 진로탐색)로 구성되어 있으며, 이 중 5개 영역에 해당되는 강의를 선택하여 수강하면 된다. 2010학년도부터는 융합형 인재 육성을 위해 수강방식이 바뀌었는데, 이때 학생들은 핵심교양, 융합교양, 실용교양을 구분하여 수강해야 한다. 2010학년도 입학자는 '핵심교양'에 포함되는 영역의 강좌를 반드시 영역별 각 1개 이상 수강하고, '융합교양'에 포함되는 영역의 강좌는 인문/자연계열 기준으로 교차 수강해야 한다.");
        //////////////////////////////////////////////////////////////////////////////////


        Essay essay = es.findEssay(essayId);
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
        return "feedback/write";
    }

    @PostMapping("/feedback/{essayId}/write")
        //첨삭 저장을 눌렀을때
    String addFeedback(Model model,
                       @SessionAttribute("loginSession") String nickname,
                       @PathVariable Long essayId,
                       @ModelAttribute EssayFeedbackInfoDTO feedback) {
        //레포지토리에 피드백 객체 저장
        Member writer = memberService.findByNickname(nickname);
        Essay essay = es.findEssay(essayId);

        feedbackService.storeFeedback(writer, essay, feedback);

        return "redirect:/essay/feedback/" + essayId + "/read/" + '0'; //첨삭 읽기로 redirect
    }

    @GetMapping("/feedback/{essayId}/read/{feedbackId}")
    String readFeedback(Model model,
                        @SessionAttribute("loginSession") String nickname,
                        @PathVariable Long essayId,
                        @PathVariable Long feedbackId,
                        @ModelAttribute EssayFeedbackInfoDTO feedback) {

        Essay essay = es.findEssay(essayId);
        Member writer = memberService.findByNickname(nickname);
        List<EssayContent> essayContents = essay.getContents();
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        List<EssayFeedbackQuestionDTO> everyComment = feedback.getEveryComment();
        EssayFeedback essayFeedback = feedbackService.findFeedback(nickname, essayId);

        model.addAttribute("questions", questions);
        model.addAttribute("answers", answers);
        model.addAttribute("essayPostName", "삼성 자소서 첨삭 해주세요!");
        return "feedback/read";
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

        ts.storeEssayTemplate(EssayTemplate.createEssayTemplate(templateTitle, etc));


        return "redirect:/essay/add";
    }

}