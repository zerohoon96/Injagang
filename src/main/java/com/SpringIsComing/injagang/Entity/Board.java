package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//게시물 Entity
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) //조인 테이블 전략
@DiscriminatorColumn
public abstract class Board {
    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    private LocalDateTime date; //작성 날짜
    private String title; //제목
    private String text; //내용 글
}
