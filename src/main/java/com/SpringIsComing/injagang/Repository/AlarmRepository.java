package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.alarm.Alarm;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.alarm.EssayAlarm;
import com.SpringIsComing.injagang.Entity.alarm.FriendAlarm;
import com.SpringIsComing.injagang.Entity.alarm.InterviewAlarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAlarmsByMember(Member member);

    @Query("select ea from EssayAlarm ea join fetch ea.member where ea.member = :m and ea.read = false")
    List<EssayAlarm> findEssayAlarmsByMember(@Param("m") Member member);

    @Query("select ia from InterviewAlarm ia join fetch ia.member where ia.member = :m and ia.read = false")
    List<InterviewAlarm> findInterviewAlarmsByMember(@Param("m") Member member);

    @Query("select fa from FriendAlarm fa join fetch fa.member where fa.member = :m and fa.read = false")
    List<FriendAlarm> findFriendAlarmsByMember(@Param("m") Member member);


}
