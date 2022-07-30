package com.github.devsanso.sns.service;


import com.github.devsanso.sns.dto.PostRegisterVODto;


import java.util.UUID;

public interface PostManageService {
    UUID addPost(UUID userUUID, PostRegisterVODto vo);
    void deletePost(UUID postUUID);
    void deletePost(UUID userUUID,UUID postUUID);
    void deleteByUserSelf(UUID userUUID);
}
