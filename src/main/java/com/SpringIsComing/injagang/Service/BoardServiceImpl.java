package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Entity.*;
import com.SpringIsComing.injagang.Entity.alarm.InterviewAlarm;
import com.SpringIsComing.injagang.Repository.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    private final EssayContentRepository essayContentRepository;
    private final EssayFeedbackRepository essayFeedbackRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewFeedbackRepository interviewFeedbackRepository;
    private final VideoRepository videoRepository;
    private final ReplyRepository replyRepository;
    private final ExpectedQuestionRepository questionRepository;
    private final AlarmRepository alarmRepository;

    @Override
    @Transactional
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
    @Transactional
    public Long registerInterview(InterviewDTO dto) {
        log.info("registerInterview => dto to entity & repository save");

        /**
         * dto의 정보를 바탕으로 새로운 interview 객체 생성
         */
        Member member = memberRepository.findByNickname(dto.getWriter());

        Interview entity = Interview.builder()
                .title(dto.getTitle())
                .text(dto.getText())
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
    @Transactional
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

    @Override
    @Transactional
    public void updateEssayBoard(Long pk, String title, String text) {
        Essay essay = essayRepository.findById(pk).get();
        essay.setTitle(title);
        essay.setText(text);
        essay.setDate(LocalDateTime.now());

        essayRepository.save(essay);
    }

    @Override
    @Transactional
    public void updateInterviewBoard(Long pk, String title, String text) {
        Interview interview = interviewRepository.findById(pk).get();
        interview.setTitle(title);
        interview.setText(text);
        interview.setDate(LocalDateTime.now());

        interviewRepository.save(interview);
    }

    @Override
    @Transactional
    public void deleteEssayBoard(Long pk) {
        Essay essay = essayRepository.findById(pk).get();
        essay.setAccess(0);
        essay.setTitle("");
        essay.setText("");

        List<Reply> replies = essay.getReplies();
        List<EssayFeedback> feedbacks = essay.getFeedbacks();
        for (Reply reply : replies) {
            replyRepository.delete(reply);
        }
        for (EssayFeedback feedback : feedbacks) {
            essayFeedbackRepository.delete(feedback);
        }

        essay.clearReplies();
        essay.clearFeedbacks();
    }

    @Override
    @Transactional
    public void deleteInterviewBoard(Long pk) {
        Interview interview = interviewRepository.findById(pk).get();
        List<InterviewAlarm> alarms = alarmRepository.findAlarmsByInterview(interview);

        List<Reply> replies = interview.getReplies();
        List<Video> videos = interview.getVideos();
        for (Reply reply : replies) {
            replyRepository.delete(reply);
        }
        for (Video video : videos) {
            videoRepository.delete(video);
            List<InterviewFeedback> feedbacks = video.getFeedbacks();
            for (InterviewFeedback feedback : feedbacks) {
                interviewFeedbackRepository.delete(feedback);
            }
        }

        alarmRepository.deleteAll(alarms);
        interviewRepository.delete(interview);
    }

    @Override
    @Transactional
    public void deleteEssayQuestion(Long question_pk) {
        questionRepository.deleteById(question_pk);
    }

    @Override
    @Transactional
    public void deleteBoardReply(Long reply_pk) {
        replyRepository.deleteById(reply_pk);
    }

    @Override
    @Transactional
    public void deleteInterviewFeedback(Long feedback_pk) {
        interviewFeedbackRepository.deleteById(feedback_pk);
    }

    @Override
    @Transactional
    public void registerExpectedQuestion(Long content_pk, String content, String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        EssayContent essayContent = essayContentRepository.findById(content_pk).get();

        ExpectedQuestion question = ExpectedQuestion.builder()
                .writer(member)
                .essayContent(essayContent)
                .text(content)
                .date(LocalDateTime.now())
                .build();

        questionRepository.save(question);
    }

    @Override
    @Transactional
    public void registerEssayReply(Long essay_pk, String content, String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        Essay essay = essayRepository.findById(essay_pk).get();

        Reply reply = Reply.builder()
                .replyer(member)
                .essay(essay)
                .content(content)
                .date(LocalDateTime.now())
                .build();

        replyRepository.save(reply);
    }

    @Override
    @Transactional
    public void registerInterviewReply(Long pk, String content, String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        Interview interview = interviewRepository.findById(pk).get();

        Reply reply = Reply.builder()
                .replyer(member)
                .interview(interview)
                .content(content)
                .date(LocalDateTime.now())
                .build();

        replyRepository.save(reply);
    }

    @Override
    @Transactional
    public void registerInterviewFeedback(Long pk, String content, String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        Video video = videoRepository.findById(pk).get();

        InterviewFeedback feedback = InterviewFeedback.builder()
                .video(video)
                .member(member)
                .comment(content)
                .date(LocalDateTime.now())
                .build();

        interviewFeedbackRepository.save(feedback);
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
