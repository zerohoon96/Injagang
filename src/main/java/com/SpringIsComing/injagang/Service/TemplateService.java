package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.TemplateDTO;
import com.SpringIsComing.injagang.Entity.EssayTemplate;
import com.SpringIsComing.injagang.Entity.EssayTemplateContent;
import com.SpringIsComing.injagang.Repository.TemplateRepository;

import java.util.ArrayList;
import java.util.List;

public interface TemplateService {

    public void storeEssayTemplate(EssayTemplate essayTemplate);

}
