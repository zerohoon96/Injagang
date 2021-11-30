package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.TemplateDTO;
import com.SpringIsComing.injagang.DTO.TemplateViewDTO;
import com.SpringIsComing.injagang.Entity.EssayTemplate;
import com.SpringIsComing.injagang.Entity.EssayTemplateContent;
import com.SpringIsComing.injagang.Repository.TemplateRepository;

import java.util.ArrayList;
import java.util.List;

public interface TemplateService {

    List<TemplateViewDTO> findTemplates();
    public void storeEssayTemplate(EssayTemplate essayTemplate);
    public TemplateDTO readTemplate();
    public TemplateViewDTO findTemplate(Long id);
}
