package com.github.devsanso.sns.dao;


import com.github.devsanso.sns.dto.StringDto;
import com.github.devsanso.sns.dto.UserEntityDto;
import com.github.devsanso.sns.dto.UserSubscriptionVODto;
import com.github.devsanso.sns.entity.UserEntity;
import com.github.devsanso.sns.repository.UserProfileRepository;
import com.github.devsanso.sns.repository.UserRepository;
import com.github.devsanso.sns.vo.UserAmendmentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDao {
    final private UserRepository userRepository;
    final private UserProfileRepository userProfileRepository;

    public void insert(UserSubscriptionVODto dto) {
        var profile = userProfileRepository.save(dto.toEntity().getUserProfile());
        var user = dto.toEntity();
        user.getUserProfile().profileUUID = profile.profileUUID;
        userRepository.saveAndFlush(user);
    }

    public void update(UserAmendmentVO amendment) {
        var user = userRepository.findById(amendment.userUUID).get();
        var profile = user.getUserProfile();

        if(amendment.name.isPresent())profile.name = amendment.name.get();
        if(amendment.imageDataUrl.isPresent())profile.imageDataUrl = amendment.imageDataUrl.get();
        if(amendment.password.isPresent())user.password = new StringDto(amendment.password.get()).toSha256HexString();
    }

    public void delete(UUID userUUID) {
        var user = userRepository.findById(userUUID).get();

        userProfileRepository.delete(user.getUserProfile());
        userRepository.delete(user);
    }

    public Optional<UserEntityDto> selectByIdOfPassword(String id, StringDto passwordDto) {
        var entity = UserEntity.builder()
                .password(passwordDto.toSha256HexString())
                .id(id)
                .build();
        var example = Example.of(entity);
        var result = userRepository.findOne(example);

        if(result.isEmpty())return Optional.empty();

        return Optional.of(new UserEntityDto(result.get()));
    }

    public  Optional<UserEntityDto> selectByID(String id) {
        var entity = UserEntity.builder()
                .id(id)
                .build();
        var example = Example.of(entity);
        var result = userRepository.findOne(example);

        if(result.isEmpty())return Optional.empty();

        return Optional.of(new UserEntityDto(result.get()));
    }

    public  Optional<UserEntityDto> selectByUUID(UUID userUUID) {
        var entity = UserEntity.builder()
                .userUUID(userUUID)
                .build();
        var example = Example.of(entity);
        var result = userRepository.findOne(example);

        if(result.isEmpty())return Optional.empty();

        return Optional.of(new UserEntityDto(result.get()));
    }
}
