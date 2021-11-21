package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Service.EssayService;
import com.SpringIsComing.injagang.Service.EssayServiceImpl;
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

    /**
     ** 자소서 입력
     */
    @GetMapping("/write")
    String essayInit() {
        return "essay/write";
    }

    @PostMapping("/write")
    String essayWrite(@RequestParam Map<String, String> allParameters, Model model,
                      RedirectAttributes redirectAttributes) {

        List<EssayContent> ec = new ArrayList<>();

        // 자소서 제목
        String name="emptyTitle";
        // 선택한 템플릿 이름
        String t_title="emptyTemplate";
        // 템플릿에 대한 질문, 답변
        LinkedHashMap<String, String> t_data = new LinkedHashMap<>();
        // 직접입력에 대한 질문, 답변
        LinkedHashMap<String, String> d_data = new LinkedHashMap<>();
//        System.out.println("allParameters = " + allParameters);

        Iterator<String> tmp_iterator = allParameters.keySet().iterator();
        while(tmp_iterator.hasNext()) {
            String key = tmp_iterator.next();
            if (key.equals("titleName")) {
                name = allParameters.get(key);
            }
            //템플릿이 선택 되었을 경우
            else if (key.equals("template_select") && (!allParameters.get(key).equals("emptyTemplate"))) {
                t_title = allParameters.get(key);
            }
            else if (key.equals("template_select") && (allParameters.get(key).equals("emptyTemplate"))) {
                //템플릿이 선택 안되었을 경우 아무것도 안함
            }
            //템플릿에 대한 질문,대답
            else if (key.indexOf("t") == 0) {
                String d1 = allParameters.get(key);
                ec.add(EssayContent.createEssayContent(key.substring(1), d1, true));
//                t_data.put(key, d1);
            }
            //직접입력에 대한 질문, 대답
            else {
                String d1 = allParameters.get(key);
                key = tmp_iterator.next();
                String d2 = allParameters.get(key);
                ec.add(EssayContent.createEssayContent(d1, d2, false));
//                d_data.put(d1, d2);
            }
        }


        //return으로 ID를 반환받고 redirect~~에 add하고 key:value로 저장하면 됨
        Long essayId = es.storeEssay(Essay.createEssay(name, t_title, ec));
        redirectAttributes.addAttribute("essayId", essayId);

        return "redirect:/essay/read/{essayId}";
//        return "essay/read";
    }


    @GetMapping("/read/{essayId}")
    String essayRead(@PathVariable Long essayId, Model model) throws Exception {
        Essay essay = es.findEssay(essayId);
        model.addAttribute("essay", essay);

        //템플릿 데이터
        List<EssayContent> tdata = new ArrayList();
        //직접입력 데이터
        List<EssayContent> ddata = new ArrayList();

        List<EssayContent> total = essay.getContents();

        for (int i=0; i<total.size(); i++) {
            if (total.get(i).isTemplate()) {
                tdata.add(total.get(i));
            }
            else {
                ddata.add(total.get(i));
            }
        }

        model.addAttribute("tdata", tdata);
        model.addAttribute("ddata", ddata);

        return "essay/read";
    }
}
