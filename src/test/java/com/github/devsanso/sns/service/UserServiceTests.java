package com.github.devsanso.sns.service;


import com.github.devsanso.sns.dao.UserDao;
import com.github.devsanso.sns.dto.StringDto;
import com.github.devsanso.sns.repository.UserProfileRepository;
import com.github.devsanso.sns.repository.UserRepository;

import com.github.devsanso.sns.utils.RandomUserAmendmentVO;
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
public class UserServiceTests {
    @Autowired
    UserManageService userManageService;
    @Autowired
    UserDao userDao;
    @Autowired
    UserProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        profileRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    public void userCreateTest() {
        var s = RandomUserSubscriptionVO.random();
        userManageService.create(s);
        var find = userDao.selectByIdOfPassword(s.getId(),new StringDto(s.getPassword()));
        Assert.assertTrue("select user",find.isPresent());
        Assert.assertEquals(find.get().toUserInfoVO().getName(),s.getName());
    }

    @Test
    public void userAmendTest() {
        var s = RandomUserSubscriptionVO.random();
        var uuid = userManageService.create(s);
        var amend = RandomUserAmendmentVO.randomAndNoImage(uuid);
        userManageService.amend(amend);

        var find = userDao.selectByUUID(uuid).get();
        Assert.assertEquals(find.toUserInfoVO().getName(),amend.name.get());
    }

    @Test
    public void userDeleteTest() {
        var s = RandomUserSubscriptionVO.random();
        var uuid = userManageService.create(s);

        var find = userDao.selectByUUID(uuid);
        Assert.assertTrue(find.isPresent());

        userManageService.deleteUser(uuid);
        find = userDao.selectByUUID(uuid);
        Assert.assertTrue(find.isEmpty());
    }
}
