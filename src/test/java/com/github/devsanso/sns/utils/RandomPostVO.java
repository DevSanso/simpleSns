package com.github.devsanso.sns.utils;

import com.github.devsanso.sns.vo.PostVO;

import java.util.Optional;

public class RandomPostVO {
    public static PostVO random() {
        return PostVO.builder()
                .title(RandomString.random(16))
                .content(RandomString.random(128))
                .headerImageDataUrl(Optional.of(RandomDataUrl.random()))
                .build();
    }
}
