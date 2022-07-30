package com.github.devsanso.sns.service;


import com.github.devsanso.sns.dao.PostDao;
import com.github.devsanso.sns.dao.UserDao;
import com.github.devsanso.sns.dto.PostRegisterVODto;
import com.github.devsanso.sns.dto.UserSubscriptionVODto;
import com.github.devsanso.sns.repository.PostRepository;
import com.github.devsanso.sns.repository.UserProfileRepository;
import com.github.devsanso.sns.repository.UserRepository;
import com.github.devsanso.sns.utils.RandomPostRegisterVO;
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

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true )
@ComponentScan("com.github.devsanso.sns")
@TestPropertySource("classpath:application.properties")
public class PostMangeServiceTests {
    @Autowired
    PostManageService service;
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
    public void addPost() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = userDao.insert(dto);

        var postUUID =service.addPost(uuid, new PostRegisterVODto(RandomPostRegisterVO.random()));

        var post = postDao.selectByUUID(postUUID);
        Assert.assertTrue(post.isPresent());
    }
    @Test
    public void deletePost() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = userDao.insert(dto);

        var postUUID =service.addPost(uuid, new PostRegisterVODto(RandomPostRegisterVO.random()));
        service.deletePost(postUUID);
        var post = postDao.selectRange(0,1);
        Assert.assertEquals(0,post.size());
    }
    @Test
    public void deletePostByUser() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = userDao.insert(dto);

        service.addPost(uuid, new PostRegisterVODto(RandomPostRegisterVO.random()));
        service.deleteByUserSelf(uuid);
        var post = postDao.selectRange(0,1);
        Assert.assertEquals(0,post.size());
    }

}
