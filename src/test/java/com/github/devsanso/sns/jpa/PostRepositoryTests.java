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
                .ImageDataUrl("")
                .title("hello world")
                .user(user)
                .build();
        var savedPost = postRepository.save(post);
        var findPosts = postRepository.findAll().get(0);
        Assert.assertEquals(user.getUserUUID(),findPosts.getUser().getUserUUID());
        var fined = postRepository.findByUser(user.getUserUUID());
        Assert.assertEquals(1,fined.size());
    }
}