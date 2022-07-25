package com.github.devsanso.sns.utils;


import com.github.devsanso.sns.vo.UserSubscriptionVO;

public class RandomUserSubscriptionVO {



    public static UserSubscriptionVO random() {
        return UserSubscriptionVO.builder()
                .id(RandomString.random(8))
                .name(RandomString.random(8))
                .password(RandomString.random(16))
                .imageUrl(RandomDataUrl.random())
                .build();
    }
}
