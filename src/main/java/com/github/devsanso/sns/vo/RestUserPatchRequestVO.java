package com.github.devsanso.sns.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RestUserPatchRequestVO {
    private String name;
    private String imageData;
    private String password;
}
