package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.Friend;
import com.SpringIsComing.injagang.Repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;


    @Override
    public List<Friend> findFriends(Long id) {

        return friendRepository.findFriendsById(id);
    }

    @Override
    public boolean isFriend(Long loginId, Long targetId) {

        return friendRepository.findFriendByIds(loginId, targetId).isPresent();

    }
}
