package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Friend;
import com.SpringIsComing.injagang.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {


    @Query("select f from Friend f join fetch f.member m where m = :m")
    List<Friend> findFriendByMember(@Param("m") Member member);

}
