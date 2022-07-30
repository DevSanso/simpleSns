package com.github.devsanso.sns.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PostInfoModel {
    public String headerImageDataUrl;
    public String title;
    public String content;
    public String author;
}
