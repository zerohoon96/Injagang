package com.SpringIsComing.injagang.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/essay")
public class EssayController {
    /**
     ** 자소서 입력
     */
    @GetMapping("/write")
    String essayInit() {
        return "essay/write";
    }

    @PostMapping("/read")
    String essayRead(@RequestParam Map<String, String> allParameters, Model model) {
        //db저장

        // 자소서 제목
        String name="";
        // 선택한 템플릿 이름
        String t_title="";
        // 템플릿에 대한 질문, 답변
        HashMap<String, String> t_data = new HashMap<>();
        // 직접입력에 대한 질문, 답변
        HashMap<String, String> d_data = new HashMap<>();
        System.out.println("allParameters = " + allParameters);
        Iterator<String> tmp_iterator = allParameters.keySet().iterator();
        while(tmp_iterator.hasNext()) {
            String key = tmp_iterator.next();
            if (key.equals("titleName")) {
                name += allParameters.get(key);
            }
            //템플릿이 선택 되었을 경우
            else if (key.equals("template_select") && (allParameters.get(key).equals("t0"))) {

            }
            else if (key.equals("template_select") && (!allParameters.get(key).equals("t0"))) {
                t_title += allParameters.get(key);
            }
            //템플릿에 대한 질문,대답
            else if (key.contains("template")) {
                String d1 = allParameters.get(key);
                t_data.put(key, d1);
            }
            //직접입력에 대한 질문, 대답
            else {
                String d1 = allParameters.get(key);
                key = tmp_iterator.next();
                String d2 = allParameters.get(key);
                d_data.put(d1, d2);
            }
        }
        System.out.println("t_title = " + t_title);
        System.out.println("t_data = " + t_data);
        System.out.println("d_data = " + d_data);

        model.addAttribute("t_title", t_title);
        model.addAttribute("t_data", t_data);
        model.addAttribute("d_data", d_data);

        return "essay/read";
    }


}
