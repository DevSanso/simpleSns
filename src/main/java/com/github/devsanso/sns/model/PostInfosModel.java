package com.github.devsanso.sns.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PostInfosModel {
    public List<PostInfoModel> data;
}
