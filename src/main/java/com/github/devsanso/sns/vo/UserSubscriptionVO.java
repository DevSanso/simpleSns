package com.github.devsanso.sns.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserSubscriptionVO {
    String id;
    String name;
    String password;
    String imageUrl;
}
