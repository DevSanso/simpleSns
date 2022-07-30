package com.github.devsanso.sns.jpa;

import com.github.devsanso.sns.entity.PostEntity;
import com.github.devsanso.sns.entity.UserEntity;
import com.github.devsanso.sns.entity.UserProfileEntity;
import com.github.devsanso.sns.repository.PostRepository;
import com.github.devsanso.sns.repository.UserProfileRepository;
import com.github.devsanso.sns.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EntityScan("com.github.devsanso.sns")
@EnableJpaRepositories("com.github.devsanso.sns")
@DataJpaTest(showSql = true )
@TestPropertySource("classpath:application.properties")
public class PostRepositoryTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserProfileRepository profileRepository;
    @Autowired
    PostRepository postRepository;

    @After
    public void cleanup() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
    private UserProfileEntity makeProfileRepository() {
        return UserProfileEntity.builder()
                .name("its me")
                .imageDataUrl(null)
                .build();
    }
    private UserEntity makeUser() {
        var profile = makeProfileRepository();

        var savedProfile = profileRepository.save(profile);
        var user = UserEntity.builder()
                .id("hello")
                .password("hello123")
                .userProfile(savedProfile)
                .build();

        return userRepository.save(user);
    }

    @Test
    public void addPostTest() {
        var user = makeUser();
        var post = PostEntity.builder()
                .content("hello world")
                .imageDataUrl("")
                .title("hello world")
                .user(user)
                .build();
        var savedPost = postRepository.save(post);
        var findPosts = postRepository.findAll().get(0);
        Assert.assertEquals(user.getUserUUID(),findPosts.getUser().getUserUUID());
        var fined = postRepository.findByUser(user.getUserUUID());
        Assert.assertEquals(1,fined.size());
    }
    @Test
    public void deleteByPostTest() {
        var user = makeUser();
        var post = PostEntity.builder()
                .content("hello world")
                .imageDataUrl("")
                .title("hello world")
                .user(user)
                .build();
        var savedPost = postRepository.save(post);
        postRepository.deleteBy(user.getUserUUID(),post.getPostUUID());
        var finds = postRepository.findAll();
        Assert.assertEquals(0,finds.size());
    }

    @Test
    public void selectRangeTest() {
        var user = makeUser();
        var post = PostEntity.builder()
                .content("hello world")
                .imageDataUrl("")
                .title("hello world")
                .user(user)
                .build();
        var post1 = PostEntity.builder()
                .content("hello world")
                .imageDataUrl("")
                .title("hello world")
                .user(user)
                .build();
        var post2 = PostEntity.builder()
                .content("hello world2")
                .imageDataUrl("")
                .title("hello world2")
                .user(user)
                .build();
        postRepository.save(post);
        postRepository.save(post1);
        postRepository.save(post2);
        var findPosts = postRepository.findAll();
        Assert.assertEquals(3,findPosts.size());

        findPosts = postRepository.findRange(0,3);
        Assert.assertEquals(3,findPosts.size());
        findPosts = postRepository.findRange(1,3);
        Assert.assertEquals(2,findPosts.size());
        findPosts = postRepository.findRange(4,2);
        Assert.assertEquals(0,findPosts.size());
    }
}
