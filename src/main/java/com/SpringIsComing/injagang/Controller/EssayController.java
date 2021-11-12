package com.SpringIsComing.injagang.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/essay")
public class EssayController {

    @GetMapping
    public String test() {
        return "essay/write";
    }
}
