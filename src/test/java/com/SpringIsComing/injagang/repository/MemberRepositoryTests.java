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
                    .login_id("user" + i + "@aaa.com")
                    .password("1234")
                    .nickname("USER"+i)
                    .info("dummy user"+i)
                    .build();

            repository.save(member);
        });
    }
}
