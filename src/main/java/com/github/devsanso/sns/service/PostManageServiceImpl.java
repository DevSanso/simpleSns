package com.github.devsanso.sns.service;


import com.github.devsanso.sns.dao.PostDao;
import com.github.devsanso.sns.dto.PostRegisterVODto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostManageServiceImpl implements PostManageService {
    final private PostDao postDao;

    @Override
    @Transactional
    public UUID addPost(UUID userUUID, PostRegisterVODto vo) {
        return postDao.add(userUUID,vo);
    }

    @Override
    @Transactional
    public void deletePost(UUID postUUID) {
        postDao.delete(postUUID);
    }

    @Override
    @Transactional
    public void deletePost(UUID userUUID, UUID postUUID) {postDao.delete(userUUID,postUUID);}

    @Override
    @Transactional
    public void deleteByUserSelf(UUID userUUID) {
        postDao.deleteByUser(userUUID);
    }
}
