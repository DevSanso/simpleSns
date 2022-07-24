package com.github.devsanso.sns.repository;

import com.github.devsanso.sns.entity.PostEntity;
import com.github.devsanso.sns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,UUID> {
    @Query(value = "SELECT * FROM posts WHERE user_uuid=:user_uuid",nativeQuery = true)
    abstract ArrayList<PostEntity> findByUser(@Param("user_uuid") UUID userUUID);
    @Query(value = "DELETE FROM posts WHERE user_uuid=:user_uuid",nativeQuery = true)
    abstract void deleteByUserUUID(@Param("user_uuid")UUID userUUID);
}
