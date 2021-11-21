package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;

public interface EssayService {

    public Long storeEssay(Essay essay);
    public Essay findEssay(Long essayId) throws Exception;

}
