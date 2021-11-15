package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.EssayDTO;
import com.SpringIsComing.injagang.DTO.InterviewDTO;
import com.SpringIsComing.injagang.DTO.PageRequestDTO;
import com.SpringIsComing.injagang.DTO.PageResultDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final EssayRepository essayRepository;
    private final InterviewRepository interviewRepository;

    @Override
    public Long registerEssay(EssayDTO dto) {
        log.info("registerEssay => dto to entity & repository save");

        Essay entity = essayDtoToEntity(dto);
        essayRepository.save(entity);

        return entity.getId();
    }

    @Override
    public Long registerInterview(InterviewDTO dto) {
        log.info("registerInterview => dto to entity & repository save");

        Interview entity = interviewDtoToEntity(dto);
        interviewRepository.save(entity);

        return entity.getId();
    }

    @Override
    @Transactional
    public PageResultDTO<EssayDTO, Essay> getEssayList(PageRequestDTO requestDTO) {
        log.info("getEssayList =>" + requestDTO);

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending()); //최신순으로 정렬
        Page<Essay> result = essayRepository.findAll(pageable);

        Function<Essay, EssayDTO> fn = (entity -> essayEntityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    @Transactional
    public PageResultDTO<InterviewDTO, Interview> getInterviewList(PageRequestDTO requestDTO) {
        log.info("getInterviewList =>" + requestDTO);

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending()); //최신순으로 정렬
        Page<Interview> result = interviewRepository.findAll(pageable);

        Function<Interview, InterviewDTO> fn = (entity -> interviewEntityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    @Transactional
    public EssayDTO readEssayBoard(Long id) { //임시
        Optional<Essay> result = essayRepository.findById(id);

        return result.isPresent() ? essayEntityToDto(result.get()) : null;
    }

    @Override
    @Transactional
    public InterviewDTO readInterviewBoard(Long id) { //임시
        Optional<Interview> result = interviewRepository.findById(id);

        return result.isPresent() ? interviewEntityToDto(result.get()) : null;
    }
}
