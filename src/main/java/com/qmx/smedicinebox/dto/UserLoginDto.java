package com.qmx.smedicinebox.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginDto {

    public UserLoginDto(String uUsername, String uPassword) {
        this.uUsername = uUsername;
        this.uPassword = uPassword;
    }
    @JsonProperty("uUsername")
    private String uUsername;
    @JsonProperty("uPassword")
    private String uPassword;
}
