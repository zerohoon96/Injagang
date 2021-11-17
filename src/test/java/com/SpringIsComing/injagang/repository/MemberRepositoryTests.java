package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository repository;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,50).forEach(i -> {
            Member member = Member.builder()
                    .loginId("userId"+i)
                    .password("1234")
                    .nickname("USER"+i)
                    .auth(true)
                    .email("user" + i + "@aaa.com")
                    .name("Name..."+i)
                    .build();

            repository.save(member);
        });
    }
}
