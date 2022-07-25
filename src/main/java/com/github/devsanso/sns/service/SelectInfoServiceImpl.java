package com.github.devsanso.sns.service;

import com.github.devsanso.sns.dao.PostDao;
import com.github.devsanso.sns.dao.UserDao;
import com.github.devsanso.sns.vo.PostVO;
import com.github.devsanso.sns.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SelectInfoServiceImpl implements SelectInfoService{
    final private UserDao userDao;
    final private PostDao postDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserInfoVO selectUserInfo(UUID userUUID) {
        var dto =  userDao.selectByUUID(userUUID);
        if(dto.isEmpty())return null;

        return dto.get().toUserInfoVO();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserInfoVO selectUserInfo(String userID) {
        var dto =  userDao.selectByID(userID);
        if(dto.isEmpty())return null;

        return dto.get().toUserInfoVO();
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public PostVO selectPost(UUID postUUID) {
        var dto = postDao.selectByUUID(postUUID);
        if(dto.isEmpty())return null;

        return dto.get().toPostVO();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ArrayList<PostVO> selectPostList(int start, int range) {
        var dtoArray = postDao.selectRange(start,range);
        return dtoArray
                .stream()
                .map((postEntityDto)->postEntityDto.toPostVO())
                .collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ArrayList<PostVO> selectPostListByUser(UUID userUUID) {
        var dtoArray = postDao.selectByUser(userUUID);
        return dtoArray
                .stream()
                .map((postEntityDto)->postEntityDto.toPostVO())
                .collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
    }
}
