package com.github.devsanso.sns.dto;

import com.github.devsanso.sns.entity.UserEntity;
import com.github.devsanso.sns.entity.UserProfileEntity;
import com.github.devsanso.sns.vo.UserSubscriptionVO;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserSubscriptionVODto {
    private UserSubscriptionVO vo;
    public UserProfileEntity toOnlyProfileEntity() {
        return UserProfileEntity.builder()
                .name(vo.getName())
                .imageDataUrl(vo.getImageUrl())
                .build();
    }
    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(vo.getId())
                .password(vo.getPassword())
                .userProfile(toOnlyProfileEntity())
                .build();
    }

    public UserEntity toEntityAndHashingPassword() {
        return UserEntity.builder()
                .id(vo.getId())
                .password(new StringDto(vo.getPassword()).toSha256HexString())
                .userProfile(toOnlyProfileEntity())
                .build();
    }
}
