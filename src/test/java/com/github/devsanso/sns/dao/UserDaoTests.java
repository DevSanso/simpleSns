package com.github.devsanso.sns.dao;


import com.github.devsanso.sns.dto.StringDto;
import com.github.devsanso.sns.dto.UserSubscriptionVODto;
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
@ComponentScan("com.github.devsanso.sns.dao")
@TestPropertySource("classpath:application.properties")
public class UserDaoTests {
    @Autowired
    UserDao dao;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @After
    public void cleanup() {
        userProfileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void insertTest() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        dao.insert(dto);

        var list = userRepository.findAll();
        Assert.assertEquals(1,list.size());
        var tuple = list.get(0);
        Assert.assertEquals(tuple.getId(),vo.getId());
        Assert.assertEquals(tuple.getPassword(),new StringDto(vo.getPassword()).toSha256HexString());
    }
    @Test
    public void updateTest() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        dao.insert(dto);
        var tuple = userRepository.findAll().get(0);
        Assert.assertEquals(vo.getImageUrl(),tuple.getUserProfile().imageDataUrl);

        var updateVO = RandomUserAmendmentVO.randomAndNoImage(tuple.getUserUUID());
        dao.update(updateVO);
        var list = userRepository.findAll();
        Assert.assertEquals(1,list.size());
        tuple = list.get(0);
        Assert.assertEquals(updateVO.name.get(),tuple.getUserProfile().name);
        Assert.assertEquals(null,tuple.getUserProfile().imageDataUrl);

        var profile = userProfileRepository.findById(tuple.getUserProfile().profileUUID);
        Assert.assertEquals(updateVO.name.get(),profile.get().name);
    }
    @Test
    public void findByIdAndPasswordTest() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        dao.insert(dto);

        var find = dao.selectByIdOfPassword(vo.getId(),new StringDto(vo.getPassword()));
        Assert.assertEquals(true,find.isPresent());
        Assert.assertEquals(vo.getName(),find.get().toUserInfoVO().getName());
    }
    @Test
    public void findByID() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        dao.insert(dto);

        var find = dao.selectByID(vo.getId());
        Assert.assertEquals(true,find.isPresent());
        Assert.assertEquals(vo.getName(),find.get().toUserInfoVO().getName());
    }
    @Test
    public void findByUUID() {
        var vo = RandomUserSubscriptionVO.random();
        var dto = new UserSubscriptionVODto(vo);
        var uuid = dao.insert(dto);

        var find = dao.selectByUUID(uuid);
        Assert.assertEquals(true,find.isPresent());
        Assert.assertEquals(vo.getName(),find.get().toUserInfoVO().getName());
    }

}
