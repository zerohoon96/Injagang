package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.TemplateDTO;
import com.SpringIsComing.injagang.Entity.EssayTemplate;
import com.SpringIsComing.injagang.Entity.EssayTemplateContent;
import com.SpringIsComing.injagang.Repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public void storeEssayTemplate(EssayTemplate essayTemplate) {
        templateRepository.save(essayTemplate);
    }

    public TemplateDTO readTemplate() {
        List<EssayTemplate> all = templateRepository.findAll();
        List<String> title = new ArrayList<>();
        List<List<String>> questions = new ArrayList<>();
        for (int i=0; i<all.size(); i++) {
            title.add(all.get(i).getTemplateTitle());
            List<String> tmp = new ArrayList<>();
            for (int j=0; j<all.get(i).getContents().size(); j++) {
                tmp.add(all.get(i).getContents().get(j).getQuestion());
            }
            questions.add(tmp);
        }

        TemplateDTO dto = TemplateDTO.builder()
                .templateTitle(title)
                .questions(questions)
                .build();

        return dto;
    }
}
