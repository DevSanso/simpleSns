package com.github.devsanso.sns.service;


import com.github.devsanso.sns.dto.PostVODto;


import java.util.UUID;

public interface PostManageService {
    UUID addPost(UUID userUUID,PostVODto vo);
    void deletePost(UUID postUUID);
    void deleteByUserSelf(UUID userUUID);
}
