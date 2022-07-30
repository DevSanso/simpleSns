package com.github.devsanso.sns.dto;


import com.github.devsanso.sns.vo.RestUserPatchRequestVO;
import com.github.devsanso.sns.vo.UserAmendmentVO;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class RestUserPatchRequestDto {
    private RestUserPatchRequestVO vo;


    public UserAmendmentVO toUserAmendmentVO(UUID userUUID) {
        var name = vo.getName();
        var password = vo.getPassword();
        var image = vo.getImageData();


        return UserAmendmentVO.builder()
                .name(name != null ? Optional.of(name) : Optional.empty())
                .password(password != null ? Optional.of(password) : Optional.empty())
                .imageDataUrl(image != null ? Optional.of(image) : Optional.empty())
                .userUUID(userUUID)
                .build();
    }
}
