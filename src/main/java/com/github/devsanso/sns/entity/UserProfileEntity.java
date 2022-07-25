package com.github.devsanso.sns.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name ="user_profiles")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserProfileEntity {
    @Id
    @Column(name="profile_uuid",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "UUID")
    public UUID profileUUID;

    @Column(nullable = false)
    public String name;
    @Column(name="image_data_url",nullable = true,columnDefinition = "LONGTEXT")
    public String imageDataUrl;



}
