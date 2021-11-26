package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.MypageFriendDTO;
import com.SpringIsComing.injagang.Entity.Friend;
import com.SpringIsComing.injagang.Entity.Member;

import java.util.List;

public interface FriendService {

    List<Friend> findFriends(Member member);

    boolean existRequest(Long loginId, Long targetId);
    void addFriendRequest(Long loginId, Long targetId);
    void acceptFriendRequest(Long loginId, Long targetId);

    void addFriend(String loginNickname, String targetNickname);


    default MypageFriendDTO toMypageDTO(Friend friend) {


        return MypageFriendDTO.builder()
                .nickname(friend.getNickname())
                .build();

    }

}
