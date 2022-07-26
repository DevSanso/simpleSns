package com.github.devsanso.sns.service;


import com.github.devsanso.sns.dao.UserDao;
import com.github.devsanso.sns.dto.UserSubscriptionVODto;
import com.github.devsanso.sns.vo.UserAmendmentVO;
import com.github.devsanso.sns.vo.UserSubscriptionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserMangeServiceImpl implements UserManageService {
    final private UserDao userDao;

    @Override
    @Transactional
    public UUID create(UserSubscriptionVO subscription) {
        var dto = new UserSubscriptionVODto(subscription);
        return userDao.insert(dto);
    }

    @Override
    @Transactional
    public void amend(UserAmendmentVO amendment) {
        userDao.update(amendment);
    }

    @Override
    @Transactional
    public void deleteUser(UUID userUUID) {
        userDao.delete(userUUID);
    }
}
