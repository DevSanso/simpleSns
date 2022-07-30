package com.github.devsanso.sns.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
@AllArgsConstructor
public class PostRegisterVO {
    public Optional<String> headerImageDataUrl;
    public String title;
    public String content;
}
