package com.ata.elastic.entity.dto;

import com.ata.elastic.entity.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Pattern;

public class UserDTO {
    @Pattern(regexp = "[\\w]{4,64}")
    private String username;

    private String nickname;

    private UserProfile profile;

    @Pattern(regexp = "^\\w{6,60}$")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
