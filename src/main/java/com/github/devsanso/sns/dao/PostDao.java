package com.github.devsanso.sns.dao;


import com.github.devsanso.sns.dto.PostEntityDto;
import com.github.devsanso.sns.dto.PostVODto;
import com.github.devsanso.sns.entity.PostEntity;
import com.github.devsanso.sns.entity.UserEntity;
import com.github.devsanso.sns.repository.PostRepository;
import com.github.devsanso.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PostDao {
    final private PostRepository postRepository;
    final private UserRepository userRepository;

    public UUID add(UUID userUUID, PostVODto dto) {
        var user = userRepository.findById(userUUID);
        if(user.isEmpty())throw new IllegalArgumentException();
        var userEntity = user.get();

        var postEntity = dto.toEntity(userEntity);
        var entity = postRepository.save(postEntity);
        return entity.postUUID;
    }

    public void delete(UUID postUUID) {
        postRepository.deleteById(postUUID);
    }

    public void deleteByUser(UserEntity user) {
        postRepository.deleteByUserUUID(user.getUserUUID());
    }
    public void deleteByUser(UUID userUUID) {
        postRepository.deleteByUserUUID(userUUID);
    }

    private PostEntityDto convertPostDto(PostEntity entity) {
        return new PostEntityDto(entity);
    }

    public ArrayList<PostEntityDto> selectByUser(UUID userUUID) {
        return postRepository.findByUser(userUUID)
                .stream()
                .map(this::convertPostDto)
                .collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
    }
    public ArrayList<PostEntityDto> selectRange(int start,int range) {
        return postRepository.findRange(start,range)
                .stream()
                .map(this::convertPostDto)
                .collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
    }

    public Optional<PostEntityDto> selectByUUID(UUID postUUID) {
        var find = postRepository.findById(postUUID);
        if(find.isEmpty())return Optional.empty();

        return Optional.of(new PostEntityDto(find.get()));
    }
}
