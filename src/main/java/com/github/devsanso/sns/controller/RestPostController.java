package com.github.devsanso.sns.controller;


import com.github.devsanso.sns.dto.HttpSessionDto;
import com.github.devsanso.sns.dto.PostRegisterVODto;
import com.github.devsanso.sns.dto.PostVODto;
import com.github.devsanso.sns.dto.RestPostAddPostRequestVO;
import com.github.devsanso.sns.model.PostInfoModel;
import com.github.devsanso.sns.model.PostInfosModel;
import com.github.devsanso.sns.model.RestPostAddResponseModel;
import com.github.devsanso.sns.service.PostManageService;
import com.github.devsanso.sns.service.SelectInfoService;
import com.github.devsanso.sns.vo.PostRegisterVO;
import com.github.devsanso.sns.vo.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class RestPostController {
    final private PostManageService manageService;
    final private SelectInfoService selectInfoService;

    @GetMapping("/list")

    public PostInfosModel postList(@RequestParam("start")String start,
                                         @RequestParam("end")String end, HttpServletResponse response) {
        ArrayList<PostVO> list = null;
        try {
            list = selectInfoService.selectPostList(Integer.parseInt(start),Integer.parseInt(end));
        }catch(Exception e) {
            response.setStatus(500);
            return null;
        }
        if(list == null)
            return null;

        response.setStatus(200);
        var castList = list
                .stream()
                .map((item)-> new PostVODto(item).toModel())
                .collect(ArrayList<PostInfoModel>::new,ArrayList::add,ArrayList::addAll);
        return new PostInfosModel(castList);

    }
    @DeleteMapping("/delete/{postUUID}")
    public void postDelete(@PathVariable("postUUID") String postUUID,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        var uuid = UUID.fromString(postUUID);
        var find = selectInfoService.selectPost(uuid);

        if(find == null) {
            response.setStatus(400);
            return;
        }

        var sessionDto =new HttpSessionDto(request.getSession(false));
        var userUUID = UUID.fromString(sessionDto.toInfo().getUserUUID());
        try {
            manageService.deletePost(uuid);
        }catch(Exception e) {
            response.setStatus(500);
            return;
        }
        response.setStatus(200);
    }
    @PostMapping("/add")
    public RestPostAddResponseModel postAdd(@RequestBody RestPostAddPostRequestVO body,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        var sessionInfo = new HttpSessionDto(request.getSession(false)).toInfo();
        var image = body.getPostImage();
        UUID uuid = null;
        var vo = PostRegisterVO.builder()
                .title(body.getTitle())
                .content(body.getContent())
                .headerImageDataUrl(image != null ? Optional.of(image) : Optional.empty())
                .build();
        try {
            uuid = manageService.addPost(UUID.fromString(sessionInfo.getUserUUID()),new PostRegisterVODto(vo));
        }catch(Exception e) {
            response.setStatus(500);
            return null;
        }
        response.setStatus(200);
        return new RestPostAddResponseModel(uuid.toString());


    }
}
