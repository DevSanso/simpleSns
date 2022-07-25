package com.github.devsanso.sns.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name ="posts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {
    @Id
    @Column(name="post_uuid",columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "UUID")
    public UUID postUUID;
    @Column(name="image_data_url",columnDefinition = "LONGTEXT")
    public String imageDataUrl;
    @Column(name="title")
    public String title;
    @Column(name="content")
    public String content;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_uuid", nullable=false, updatable=false)
    public UserEntity user;
}
