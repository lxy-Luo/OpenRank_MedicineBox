package com.qmx.smedicinebox.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
public class UserRegisterDto {
    public UserRegisterDto(String uName, String uUsername, String uPassword, String uEmail, String uPhone, Integer uGender) {
        this.uName = uName;
        this.uUsername = uUsername;
        this.uPassword = uPassword;
        this.uEmail = uEmail;
        this.uPhone = uPhone;
        this.uGender = uGender;
    }

    @JsonProperty("uName")
    private String uName;

    @JsonProperty("uUsername")
    private String uUsername;

    @JsonProperty("uPassword")
    private String uPassword;

    @JsonProperty("uEmail")
    private String uEmail;

    @JsonProperty("uPhone")
    private String uPhone;

    @JsonProperty("uGender")
    private Integer uGender;

}
