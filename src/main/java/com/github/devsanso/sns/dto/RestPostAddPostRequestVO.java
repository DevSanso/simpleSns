package com.github.devsanso.sns.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RestPostAddPostRequestVO {
    private String content;
    private String postImage;
    private String title;
}
