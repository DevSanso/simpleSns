package com.github.devsanso.sns.dto;

import com.github.devsanso.sns.model.UserInfoModel;
import com.github.devsanso.sns.vo.UserInfoVO;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserInfoVODto {
    private UserInfoVO vo;


    public UserInfoModel toModel() {
        var imageOption = vo.getImageDataUrl();
        return new UserInfoModel(vo.getName(), imageOption.orElse(null));
    }
}
