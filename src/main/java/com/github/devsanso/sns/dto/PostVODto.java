package com.github.devsanso.sns.dto;


import com.github.devsanso.sns.entity.PostEntity;
import com.github.devsanso.sns.entity.UserEntity;
import com.github.devsanso.sns.vo.PostVO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostVODto {
    private PostVO vo;

    private String DataUrl() {
        if(vo.headerImageDataUrl.isEmpty())return null;

        return vo.headerImageDataUrl.get();
    }

    public PostEntity toEntity(UserEntity user) {
        return PostEntity.builder()
                .user(user)
                .title(vo.title)
                .content(vo.content)
                .imageDataUrl(DataUrl())
                .build();
    }
    public PostEntity toNoUserEntity() {
        return PostEntity.builder()
                .title(vo.title)
                .content(vo.content)
                .imageDataUrl(DataUrl())
                .build();
    }
}
