package com.github.devsanso.sns.utils;

import com.github.devsanso.sns.vo.PostRegisterVO;

import java.util.Optional;

public class RandomPostRegisterVO {
    public static PostRegisterVO random() {
        return PostRegisterVO.builder()
                .title(RandomString.random(16))
                .content(RandomString.random(128))
                .headerImageDataUrl(Optional.of(RandomDataUrl.random()))
                .build();
    }
}
