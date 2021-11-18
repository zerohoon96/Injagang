package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f from Friend f where f.memberA_id = :loginId")
    List<Friend> findFriendsById(@Param("loginId") Long loginId);

    @Query("select f from Friend f where f.memberA_id = :loginId AND f.memberB_id = :targetId")
    Optional<Friend> findFriendByIds(@Param("loginId") Long loginId, @Param("targetId") Long targetId);


}
