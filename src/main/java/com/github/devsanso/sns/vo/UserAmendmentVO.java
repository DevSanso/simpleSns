package com.github.devsanso.sns.vo;

import java.util.Optional;
import java.util.UUID;

public class UserAmendmentVO {
    public UUID userUUID;
    public Optional<String> name;
    public Optional<String> imageDataUrl;
    public Optional<String> password;
}
