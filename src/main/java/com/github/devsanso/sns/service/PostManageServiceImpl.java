package com.github.devsanso.sns.service;


import com.github.devsanso.sns.dao.PostDao;
import com.github.devsanso.sns.dto.PostVODto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostManageServiceImpl implements PostManageService {
    final private PostDao postDao;

    @Override
    @Transactional
    public UUID addPost(UUID userUUID,PostVODto vo) {
        return postDao.add(userUUID,vo);
    }

    @Override
    public void deletePost(UUID postUUID) {
        postDao.delete(postUUID);
    }

    @Override
    public void deleteByUserSelf(UUID userUUID) {
        postDao.deleteByUser(userUUID);
    }
}
