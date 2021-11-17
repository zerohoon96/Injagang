package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.MypageEssayDTO;
import com.SpringIsComing.injagang.DTO.MypageFriendDTO;
import com.SpringIsComing.injagang.Entity.Friend;

import java.util.List;

public interface FriendService {

    List<Friend> findFriends(Long id);

    boolean isFriend(Long loginId, Long targetId);

    default MypageFriendDTO toMypageDTO(Friend friend, String nickname) {


        return MypageFriendDTO.builder()
                .id(friend.getId())
                .nickname(nickname)
                .build();

    }
}
