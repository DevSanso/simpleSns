package com.github.devsanso.sns.service;

import com.github.devsanso.sns.vo.UserAmendmentVO;
import com.github.devsanso.sns.vo.UserSubscriptionVO;

import java.util.UUID;

public interface UserManageService {
    UUID create(UserSubscriptionVO subscription);
    void amend(UserAmendmentVO amendment);
    void deleteUser(UUID userUUID);
}
