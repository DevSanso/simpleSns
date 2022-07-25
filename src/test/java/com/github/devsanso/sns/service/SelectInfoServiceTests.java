package com.github.devsanso.sns.service;


import com.github.devsanso.sns.dao.PostDao;
import com.github.devsanso.sns.dao.UserDao;
import com.github.devsanso.sns.dto.PostVODto;
import com.github.devsanso.sns.dto.UserSubscriptionVODto;
import com.github.devsanso.sns.repository.PostRepository;
import com.github.devsanso.sns.repository.UserProfileRepository;
import com.github.devsanso.sns.repository.UserRepository;
import com.github.devsanso.sns.utils.RandomPostVO;
import com.github.devsanso.sns.utils.RandomUserSubscriptionVO;
import com.github.devsanso.sns.vo.PostVO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true )
@ComponentScan("com.github.devsanso.sns")
@TestPropertySource("classpath:application.properties")
public class SelectInfoServiceTests {
    @Autowired
    SelectInfoService service;
    @Autowired
    UserDao userDao;
    @Autowired
    PostDao postDao;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        postRepository.deleteAll();
        userProfileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void selectUserByUserIDTest(){
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = userDao.insert(dto);
        var find = service.selectUserInfo(vo.getId());

        Assert.assertNotEquals(null,find);
        Assert.assertEquals(find.getName(),vo.getName());
        Assert.assertEquals(find.getImageDataUrl().get(),vo.getImageUrl());
    }
    @Test
    public void selectUserByUserUUIDTest(){
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = userDao.insert(dto);
        var find = service.selectUserInfo(uuid);

        Assert.assertNotEquals(null,find);
        Assert.assertEquals(find.getName(),vo.getName());
        Assert.assertEquals(find.getImageDataUrl().get(),vo.getImageUrl());
    }
    @Test
    public void selectPostTest(){
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = userDao.insert(dto);

        var postVO = RandomPostVO.random();
        var postUUID = postDao.add(uuid,new PostVODto(postVO));
        var find = service.selectPost(postUUID);
        Assert.assertNotEquals(null,find);
        Assert.assertEquals(postVO.content,find.content);
        Assert.assertEquals(postVO.title,find.title);
    }


    @Test
    public void selectPostListTest(){
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = userDao.insert(dto);

        var count = 10;

        for(int i = 0;i<count;i++) {
            var postVO = RandomPostVO.random();
            postDao.add(uuid,new PostVODto(postVO));
        }

        var find = service.selectPostList(3,10);
        Assert.assertEquals(count-3,find.size());
    }
    @Test
    public void selectPostListByUserTest(){
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = userDao.insert(dto);

        vo = RandomUserSubscriptionVO.random();
        dto = new UserSubscriptionVODto(vo);
        var uuid1 = userDao.insert(dto);

        var count = 10;

        for(int i = 0;i<count-5;i++) {
            var postVO = RandomPostVO.random();
            postDao.add(uuid,new PostVODto(postVO));
        }
        for(int i = 0;i<count;i++) {
            var postVO = RandomPostVO.random();
            postDao.add(uuid1,new PostVODto(postVO));
        }

        var find = service.selectPostListByUser(uuid);
        var find1 = service.selectPostListByUser(uuid1);
        Assert.assertEquals(count-5,find.size());
        Assert.assertEquals(count,find1.size());
    }
}
