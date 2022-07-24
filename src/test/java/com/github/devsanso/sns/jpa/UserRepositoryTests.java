package com.github.devsanso.sns.jpa;


import com.github.devsanso.sns.SnsApplication;
import com.github.devsanso.sns.entity.UserEntity;
import com.github.devsanso.sns.entity.UserProfileEntity;
import com.github.devsanso.sns.repository.UserProfileRepository;
import com.github.devsanso.sns.repository.UserRepository;
import org.h2.engine.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@EntityScan("com.github.devsanso.sns")
@EnableJpaRepositories("com.github.devsanso.sns")
@DataJpaTest(showSql = true )
@TestPropertySource("classpath:application.properties")
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserProfileRepository profileRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    private UserProfileEntity makeProfileRepository() {
        return UserProfileEntity.builder()
                .name("its me")
                .imageDataUrl(null)
                .build();
    }

    @Test
    public void addUserTests() {
       var profile = makeProfileRepository();

       var savedProfile = profileRepository.save(profile);
        var user = UserEntity.builder()
                .id("hello")
                .password("hello123")
                .userProfile(savedProfile)
                .build();

       userRepository.save(user);
       var saved = userRepository.findAll().get(0);
       Assert.assertEquals(user.id,saved.id);
       Assert.assertEquals(user.password,saved.password);
       Assert.assertEquals(user.getUserProfile().name,profile.name);
    }


}
