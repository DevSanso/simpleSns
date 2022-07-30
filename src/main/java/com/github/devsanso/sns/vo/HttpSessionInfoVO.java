package com.github.devsanso.sns.vo;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HttpSessionInfoVO {
    private String ip;
    private String userUUID;
}
