package com.SpringIsComing.injagang.test;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Test {

    @Id
    @GeneratedValue
    Integer id;

    String a;
    String b;
}
