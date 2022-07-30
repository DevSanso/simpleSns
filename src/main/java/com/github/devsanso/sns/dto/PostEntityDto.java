package com.github.devsanso.sns.dto;


import com.github.devsanso.sns.entity.PostEntity;
import com.github.devsanso.sns.vo.PostRegisterVO;
import com.github.devsanso.sns.vo.PostVO;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class PostEntityDto {
    private PostEntity entity;

    public PostRegisterVO toPostRegisterVO() {
        return PostRegisterVO.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .headerImageDataUrl(Optional.of(entity.getImageDataUrl()))
                .build();
    }

    public PostVO toPostVO() {
        var image = entity.getImageDataUrl();
        return PostVO.builder()
                .author(entity.getUser().getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .headerImageDataUrl(entity.getImageDataUrl() != null? Optional.of(image) : Optional.empty())
                .build();
    }
}
