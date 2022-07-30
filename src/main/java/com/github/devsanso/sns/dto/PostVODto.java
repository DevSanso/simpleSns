package com.github.devsanso.sns.dto;


import com.github.devsanso.sns.model.PostInfoModel;
import com.github.devsanso.sns.vo.PostVO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostVODto {
    private PostVO vo;

    public PostInfoModel toModel() {
        var image = vo.getHeaderImageDataUrl();
        return PostInfoModel.builder()
                .title(vo.getTitle())
                .author(vo.getAuthor())
                .content(vo.getContent())
                .headerImageDataUrl(image.orElse(null))
                .build();
    }
}
