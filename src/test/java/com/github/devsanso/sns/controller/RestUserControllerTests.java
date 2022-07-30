package com.github.devsanso.sns.controller;


import com.github.devsanso.sns.service.SelectInfoService;
import com.github.devsanso.sns.service.UserManageService;
import com.github.devsanso.sns.utils.RandomUserSubscriptionVO;
import com.github.devsanso.sns.vo.RestUserPatchRequestVO;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.github.devsanso.sns")
@TestPropertySource("classpath:application.properties")
public class RestUserControllerTests {
    public MockHttpSession session;
    public MockMvc mvc;
    @Autowired
    WebApplicationContext context;

    @Autowired
    UserManageService service;
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
        }catch(Exception e){}
        userUUID = null;
        vo = null;
        session = null;
    }

    @Test
    public void getUserTest() throws Exception {
        var builder = get("/user/{uuid}",userUUID);
        var res = mvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse();
        var json = new JSONObject(res.getContentAsString());
        Assert.assertEquals(vo.getName(),(String)json.get("name"));
        Assert.assertEquals(vo.getImageUrl(),(String)json.get("imageData"));
    }

    @Test
    public void patchUserTest() throws Exception {
        var body = RestUserPatchRequestVO.builder()
                .name("hello")
                .password("hello")
                .build();
        var builder = patch("/user")
                .content(new JSONObject(body).toString())
                .contentType(MediaType.APPLICATION_JSON)
                .session(session);
        var res = mvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse();
        var selected = selectService.selectUserInfo(userUUID);
        Assert.assertEquals(selected.getName(),body.getName());
    }
    @Test
    public void deleteUserTest() throws Exception {
        var builder = delete("/user")
                .session(session);
        var res = mvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse();
        var existed = selectService.selectUserInfo(userUUID);
        Assert.assertNull(existed);
    }

}
