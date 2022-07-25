package com.github.devsanso.sns.utils;

import com.github.devsanso.sns.vo.UserAmendmentVO;

import java.util.Optional;
import java.util.UUID;


public class RandomUserAmendmentVO {
    public static UserAmendmentVO randomAndNoImage(UUID userUUID) {
        return UserAmendmentVO.builder()
                .userUUID(userUUID)
                .name(Optional.of(RandomString.random(8)))
                .password(Optional.of(RandomString.random(8)))
                .imageDataUrl(Optional.empty())
                .build();

    }
}
