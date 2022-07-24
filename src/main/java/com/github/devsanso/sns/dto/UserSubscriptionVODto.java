package com.github.devsanso.sns.dto;

import com.github.devsanso.sns.entity.UserEntity;
import com.github.devsanso.sns.entity.UserProfileEntity;
import com.github.devsanso.sns.vo.UserSubscriptionVO;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserSubscriptionVODto {
    private UserSubscriptionVO vo;
    private UserProfileEntity toProfileEntity() {
        return UserProfileEntity.builder()
                .name(vo.getName())
                .imageDataUrl(vo.getImageUrl())
                .build();
    }
    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(vo.getId())
                .password(vo.getPassword())
                .userProfile(toProfileEntity())
                .build();
    }
}
