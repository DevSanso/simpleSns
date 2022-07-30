package com.github.devsanso.sns.service;

import com.github.devsanso.sns.vo.PostRegisterVO;
import com.github.devsanso.sns.vo.PostVO;
import com.github.devsanso.sns.vo.UserInfoVO;

import java.util.ArrayList;
import java.util.UUID;

public interface SelectInfoService {
    UserInfoVO selectUserInfo(UUID userUUID);
    UserInfoVO selectUserInfo(String userID);
    PostVO selectPost(UUID postUUID);
    ArrayList<PostVO> selectPostList(int start, int range);
    ArrayList<PostVO> selectPostListByUser(UUID userUUID);
}
