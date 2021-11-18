package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EssayServiceImpl implements EssayService{

    private final EssayRepository essayRepository;

    @Override
    public List<Essay> findEssays(Member member) {

        return essayRepository.findEssaysByMember(member);

    }
}
