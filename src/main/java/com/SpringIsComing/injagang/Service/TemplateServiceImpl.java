package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.EssayTemplate;
import com.SpringIsComing.injagang.Repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public void storeEssayTemplate(EssayTemplate essayTemplate) {
        templateRepository.save(essayTemplate);
    }
}
