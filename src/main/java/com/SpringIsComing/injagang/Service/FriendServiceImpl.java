package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.Friend;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.FriendRepository;
import com.SpringIsComing.injagang.Repository.FriendRequestRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;


    /**
     * Friend Request
     */


    @Override
    public boolean existRequest(Long loginId, Long targetId) {

        return friendRequestRepository.findFriendByIds(loginId, targetId).isPresent();

    }

    @Override
    public void addFriend(String loginNickname, String targetNickname) {
        Member loginMember = memberRepository.findByNickname(loginNickname);
        Member targetMember = memberRepository.findByNickname(targetNickname);

        Friend friend1 = Friend.builder()
                .member(loginMember)
                .nickname(targetNickname)
                .build();

        Friend friend2 = Friend.builder()
                .member(targetMember)
                .nickname(loginNickname)
                .build();

        friendRepository.save(friend1);
        friendRepository.save(friend2);

    }

    /**
     * Friend
     */

    @Override
    public List<Friend> findFriends(Member member) {

        return friendRepository.findFriendByMember(member);

    }

}