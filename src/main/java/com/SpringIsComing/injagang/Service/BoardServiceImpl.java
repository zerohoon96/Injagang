package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import com.SpringIsComing.injagang.Repository.VideoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final MemberRepository memberRepository;
    private final EssayRepository essayRepository;
    private final InterviewRepository interviewRepository;
    private final VideoRepository videoRepository;

    @Override
    public Long registerEssay(EssayDTO dto) {
        log.info("registerEssay => dto to entity & repository save");

        /**
         * 기존에 있던 자소서를 불러와 제목, 내용, 공개범위, 시각 정보를 추가하고 db에 저장
         */
        Essay entity = essayRepository.findById(dto.getPk()).get();
        entity.setAccess(2); //공개범위 수정
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getText());
        entity.setDate(LocalDateTime.now());
        essayRepository.save(entity);

        return entity.getId();
    }

    @Override
    public Long registerInterview(InterviewDTO dto) {
        log.info("registerInterview => dto to entity & repository save");

        /**
         * dto의 정보를 바탕으로 새로운 interview 객체 생성
         */
        Member member = memberRepository.findByNickname(dto.getWriter());

        Interview entity = Interview.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .access(2)
                .writer(member)
                .date(LocalDateTime.now())
                .build();

        interviewRepository.save(entity);

        for (String videoUrl : dto.getVideoUrls()) {
            String videoName = videoUrl.split("_")[1]; //원본 파일명
            Video video = Video.builder()
                    .videoURL(videoUrl)
                    .videoName(videoName)
                    .date(LocalDateTime.now())
                    .interview(entity)
                    .build();

            videoRepository.save(video);
        }

        return entity.getId();
    }

    //로그인한 사람이 쓴 자소서 DTO들을 찾아 리턴
    public List<EssayDTO> getEssays(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        List<Essay> essayList = member.getEssays();
        List<EssayDTO> dtoList = new ArrayList<>();

        for (Essay essay : essayList) {
            //공개 권한이 private이나 친구공개인 경우에만 선택
            if(essay.getAccess() == null || essay.getAccess() < 2) {
                dtoList.add(EssayDTO.builder()
                        .pk(essay.getId())
                        .essayTitle(essay.getEssayTitle())
                        .build());
            }
        }

        return dtoList;
    }

    //로그인한 사람이 쓴 면접 DTO들을 찾아 리턴
    public List<InterviewDTO> getInterviews (String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        List<Interview> interviewList = member.getInterviews();
        List<InterviewDTO> dtoList = new ArrayList<>();

        for (Interview interview : interviewList) {
            //공개 권한이 private이나 친구공개인 경우에만 선택
            if(interview.getAccess() == null || interview.getAccess() < 2) {
                dtoList.add(InterviewDTO.builder()
                        .build());
            }
        }

        return dtoList;
    }

    @Override
    @Transactional
    public PageResultDTO<EssayDTO, Essay> getEssayList(PageRequestDTO requestDTO) {
        log.info("getEssayList =>" + requestDTO);

        Pageable pageable = requestDTO.getPageable(Sort.by("date").descending()); //최신순으로 정렬
        BooleanBuilder booleanBuilder = getSearchEssay(requestDTO);

        Page<Essay> result = essayRepository.findAll(booleanBuilder, pageable);

        Function<Essay, EssayDTO> fn = (entity -> essayEntityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    @Transactional
    public PageResultDTO<InterviewDTO, Interview> getInterviewList(PageRequestDTO requestDTO) {
        log.info("getInterviewList =>" + requestDTO);

        Pageable pageable = requestDTO.getPageable(Sort.by("date").descending()); //최신순으로 정렬
        BooleanBuilder booleanBuilder = getSearchInterview(requestDTO);

        Page<Interview> result = interviewRepository.findAll(booleanBuilder, pageable);

        Function<Interview, InterviewDTO> fn = (entity -> interviewEntityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    @Transactional
    public EssayBoardDTO readEssayBoard(Long id) {
        Optional<Essay> result = essayRepository.findById(id);

        return result.isPresent() ? essayBoardEntityToDto(result.get()) : null;
    }

    @Override
    @Transactional
    public InterviewBoardDTO readInterviewBoard(Long id) {
        Optional<Interview> result = interviewRepository.findById(id);

        return result.isPresent() ? interviewBoardEntityToDto(result.get()) : null;
    }

    private BooleanBuilder getSearchEssay(PageRequestDTO requestDTO) { //Querydsl 처리
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QEssay qEssay = QEssay.essay;

        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qEssay.id.gt(0L);
        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0)
            return booleanBuilder;

        //검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        //제목 검색 기능
        if(type.contains("t")) {
            conditionBuilder.or(qEssay.title.contains(keyword));
        }
        //작성자 검색 기능
        if(type.contains("w")) {
            conditionBuilder.or(qEssay.writer.nickname.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

    private BooleanBuilder getSearchInterview(PageRequestDTO requestDTO) { //Querydsl 처리
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QInterview qInterview = QInterview.interview;

        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qInterview.id.gt(0L);
        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0)
            return booleanBuilder;

        //검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        //제목 검색 기능
        if(type.contains("t")) {
            conditionBuilder.or(qInterview.title.contains(keyword));
        }
        //작성자 검색 기능
        if(type.contains("w")) {
            conditionBuilder.or(qInterview.writer.nickname.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}
