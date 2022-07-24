package com.github.devsanso.sns.repository;

import com.github.devsanso.sns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;



@Repository
public interface UserRepository  extends JpaRepository<UserEntity, UUID> { }
