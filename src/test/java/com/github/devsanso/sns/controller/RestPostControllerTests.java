package com.github.devsanso.sns.controller;


import com.github.devsanso.sns.dto.PostVODto;
import com.github.devsanso.sns.dto.RestPostAddPostRequestVO;
import com.github.devsanso.sns.model.PostInfoModel;
import com.github.devsanso.sns.model.PostInfosModel;
import com.github.devsanso.sns.service.PostManageService;
import com.github.devsanso.sns.service.SelectInfoService;
import com.github.devsanso.sns.service.UserManageService;
import com.github.devsanso.sns.utils.RandomUserSubscriptionVO;
import com.github.devsanso.sns.vo.UserSubscriptionVO;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.github.devsanso.sns")
@ComponentScan("com.github.devsanso.sns.configure")
@TestPropertySource("classpath:application.properties")
public class RestPostControllerTests {
    public MockHttpSession session;
    public MockMvc mvc;
    @Autowired
    WebApplicationContext context;

    @Autowired
    UserManageService service;
    @Autowired
    PostManageService postService;
    @Autowired
    SelectInfoService selectService;

    public UserSubscriptionVO vo;
    public UUID userUUID;

    @Before
    public  void setup() {
        vo = RandomUserSubscriptionVO.random();
        userUUID = service.create(vo);



        session = new MockHttpSession();
        session.setAttribute("userUUID",userUUID.toString());
        session.setAttribute("clientIp","127.0.0.1");
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @After
    public void clean() {
        try {
            service.deleteUser(userUUID);
            postService.deleteByUserSelf(userUUID);
        }catch(Exception e){}
        userUUID = null;
        vo = null;
        session = null;
    }
    private UUID pushPost(RestPostAddPostRequestVO body)throws Exception {
        var build = post("/post/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject(body).toString())
                .session(session);

        var res = mvc.perform(build).andExpect(status().isOk()).andReturn().getResponse();
        var object = new JSONObject(res.getContentAsString());
        var resUUID = (String)object.get("postUUID");
        return UUID.fromString(resUUID);
    }
    @Test
    public void addTests() throws Exception {
        var body = RestPostAddPostRequestVO.builder()
                .title("hello")
                .content("hello")
                .postImage(null)
                .build();
        var resUUID = pushPost(body);

        var find = selectService.selectPost(resUUID);
        Assert.assertEquals(find.content,body.getContent());
        Assert.assertEquals(find.title,body.getTitle());
    }
    @Test
    public void deleteTests() throws Exception {
        var body = RestPostAddPostRequestVO.builder()
                .title("hello")
                .content("hello")
                .postImage(null)
                .build();
        var resUUID = pushPost(body);
        var build = delete("/post/delete/{uuid}",resUUID)
                .session(session);
        mvc.perform(build).andExpect(status().isOk());

        var find = selectService.selectPost(resUUID);
        Assert.assertNull(find);
    }

    @Test
    public void selectListTests() throws Exception {
        var body = RestPostAddPostRequestVO.builder()
                .title("hello")
                .content("hello")
                .postImage(null)
                .build();
        var resUUID = pushPost(body);

        var build = get("/post/list")
                .param("start",Integer.toString(0))
                .param("end", Integer.toString(1));

        var res = mvc.perform(build).andExpect(status().isOk()).andReturn().getResponse();
    }
}
