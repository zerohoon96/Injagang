package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EssayServiceImpl implements EssayService {

    private final EssayRepository essayRepository;
//    RequiredArgsConstructor -> final이 붙은것들 생성자 만들어줌
//    생성자는 하나만 있으면 자동으로 추가 해줌
//    @Autowired
//    public EssayServiceImpl(EssayRepository essayRepository) {
//        this.essayRepository = essayRepository;
//    }

    @Override
    public void storeEssay(Essay essay) {
        essayRepository.save(essay);
    }
}
