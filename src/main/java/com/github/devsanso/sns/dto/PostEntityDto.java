package com.github.devsanso.sns.dto;


import com.github.devsanso.sns.entity.PostEntity;
import com.github.devsanso.sns.vo.PostVO;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class PostEntityDto {
    private PostEntity entity;

    public PostVO toPostVO() {
        return PostVO.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .headerImageDataUrl(Optional.of(entity.getImageDataUrl()))
                .build();
    }
}
