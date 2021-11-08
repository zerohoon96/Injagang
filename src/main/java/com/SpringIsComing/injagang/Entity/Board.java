package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//게시물 Entity
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Board {
    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private LocalDateTime date; //작성 날짜
    private String title; //제목
    private String text; //내용 글
}
