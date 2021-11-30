package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.TemplateDTO;
import com.SpringIsComing.injagang.DTO.TemplateViewDTO;
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

    @Override
    public List<TemplateViewDTO> findTemplates() {
        List<EssayTemplate> allTemplates = templateRepository.findAll();
        List<TemplateViewDTO> dtoList = new ArrayList<>();

        allTemplates.forEach(template -> {
            List<String> questions = new ArrayList<>();

            template.getContents().forEach(templateContent -> {
                questions.add(templateContent.getQuestion());
            });

            dtoList.add(TemplateViewDTO.builder()
                    .id(template.getId())
                    .title(template.getTemplateTitle())
                    .questions(questions)
                    .checked(template.getChecked())
                    .build());
        });

        return dtoList;
    }

    @Override
    public TemplateDTO readTemplate() {
        List<EssayTemplate> all = templateRepository.findEssayTemplatesByChecked(true);
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

    @Override
    public TemplateViewDTO findTemplate(Long id) {
        EssayTemplate entity = templateRepository.findById(id).get();

        List<EssayTemplateContent> contents = entity.getContents();
        List<String> questions = new ArrayList<>();

        contents.forEach(content -> {
            questions.add(content.getQuestion());
        });

        TemplateViewDTO dto = TemplateViewDTO.builder()
                .id(entity.getId())
                .title(entity.getTemplateTitle())
                .questions(questions)
                .build();

        return dto;
    }

    @Override
    public void grantTemplate(Long id) {
        EssayTemplate essayTemplate = templateRepository.findById(id).get();

        essayTemplate.setChecked(true);
    }

    @Override
    public void revokeTemplate(Long id) {
        EssayTemplate essayTemplate = templateRepository.findById(id).get();

        essayTemplate.setChecked(false);
    }

    @Override
    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }
}
