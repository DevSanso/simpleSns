package com.github.devsanso.sns.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name ="users",indexes = @Index(name="user_id_index",columnList = "id"))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Column(nullable = false,unique = true)
    public String id;
    @Column(nullable = false)
    public String password;

    @Column(name = "user_uuid",columnDefinition = "BINARY(16)")
    @Id
    @GeneratedValue(generator = "UUID")
    public UUID userUUID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_uuid",columnDefinition = "BINARY(16)")
    UserProfileEntity userProfile;
}
