package com.github.devsanso.sns.repository;


import com.github.devsanso.sns.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity,UUID> {
}
