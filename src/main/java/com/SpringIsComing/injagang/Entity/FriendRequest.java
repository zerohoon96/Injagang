package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//친구 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRIENDREQUEST_ID")
    private Long id;

    //서로 친구 맺은 회원들의 PK
    private Long memberA_id;
    private Long memberB_id;

}
