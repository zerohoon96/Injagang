package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.QEssay;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class EssayRepositoryTests {

    @Autowired
    private EssayRepository essayRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertEssays(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Long member_id = (long)(Math.random()*50) + 1;
            Member member = memberRepository.findById(member_id).get();

            Essay essay = Essay.builder()
                    .title("Essay Board Title..."+i)
                    .text("Essay Content..."+i)
                    .access(2)
                    .essayTitle("Sample Essay Title..."+i)
                    .date(LocalDateTime.now())
                    .writer(member)
                    .build();

            essayRepository.save(essay);
        });
    }

    @Test
    public void testQuery() {
        Pageable pageable = PageRequest.of(6,10, Sort.by("id").descending());

        QEssay qEssay = QEssay.essay;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qEssay.title.contains(keyword);
        builder.and(expression);
        Page<Essay> result = essayRepository.findAll(builder, pageable);

        result.stream().forEach(essay -> {
            System.out.println(essay.getTitle());
        });
    }
}
