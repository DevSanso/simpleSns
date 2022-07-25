package com.github.devsanso.sns.dao;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.devsanso.sns.dto.PostVODto;
import com.github.devsanso.sns.dto.UserSubscriptionVODto;
import com.github.devsanso.sns.repository.PostRepository;
import com.github.devsanso.sns.repository.UserProfileRepository;
import com.github.devsanso.sns.repository.UserRepository;
import com.github.devsanso.sns.utils.RandomPostVO;
import com.github.devsanso.sns.utils.RandomUserSubscriptionVO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true )
@ComponentScan("com.github.devsanso.sns.dao")
@TestPropertySource("classpath:application.properties")
public class PostDaoTests {
    @Autowired
    UserDao dao;
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
    public void addTest() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = dao.insert(dto);


        var postUUID = postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        var find = postRepository.findById(postUUID);
        Assert.assertEquals(true,find.isPresent());
    }

    @Test
    public void deleteTest() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = dao.insert(dto);
        var postUUID = postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        var find = postRepository.findAll();
        Assert.assertEquals(1,find.size());
        postDao.delete(postUUID);
        find = postRepository.findAll();
        Assert.assertEquals(0,find.size());
    }
    @Test
    public void deleteByUserUUIDTest() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = dao.insert(dto);
        var postUUID = postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        var find = postRepository.findAll();

        Assert.assertEquals(1,find.size());
        postDao.deleteByUser(UUID.randomUUID());
        find = postRepository.findAll();
        Assert.assertEquals(1,find.size());

        postDao.deleteByUser(uuid);
        find = postRepository.findAll();
        Assert.assertEquals(0,find.size());
    }

    @Test
    public void selectByUser() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = dao.insert(dto);

        postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        postDao.add(uuid,new PostVODto(RandomPostVO.random()));

        Exception e = null;
        try {
            postDao.add(UUID.randomUUID(),new PostVODto(RandomPostVO.random()));
        }catch(Exception ee) {
            e = ee;
        }
        Assert.assertNotEquals(null,e);
        var find = postDao.selectByUser(uuid);
        Assert.assertEquals(4,find.size());
    }

    @Test
    public void SelectRange() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = dao.insert(dto);

        var find = postDao.selectRange(1,3);
        Assert.assertEquals(0,find.size());

        postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        postDao.add(uuid,new PostVODto(RandomPostVO.random()));
        postDao.add(uuid,new PostVODto(RandomPostVO.random()));

        find = postDao.selectRange(3,4);
        Assert.assertEquals(1,find.size());
    }
}
