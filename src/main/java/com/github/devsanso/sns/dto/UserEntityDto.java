package com.github.devsanso.sns.dto;

import com.github.devsanso.sns.entity.UserEntity;
import com.github.devsanso.sns.vo.UserInfoVO;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserEntityDto {
    private UserEntity entity;

    private Optional<String> imageDataUrlOption(String image) {
        if(image == null)return Optional.empty();

        return Optional.of(image);
    }
    public UserInfoVO toUserInfoVO() {
        var profile =entity.getUserProfile();
        return UserInfoVO.builder()
                .userUUID(entity.getUserUUID().toString())
                .name(profile.name)
                .imageDataUrl(imageDataUrlOption(profile.imageDataUrl))
                .build();
    }
}
