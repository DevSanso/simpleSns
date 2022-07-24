package com.github.devsanso.sns.vo;


import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class UserInfoVO {
    private String name;
    private String userUUID;
    private Optional<String> imageDataUrl;
}
