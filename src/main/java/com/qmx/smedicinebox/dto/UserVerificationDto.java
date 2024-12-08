package com.qmx.smedicinebox.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qmx.smedicinebox.valid.LoginGroup;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
@Data
public class UserVerificationDto {
    public UserVerificationDto(String uName, String uUsername, String uPassword, String uEmail, String uPhone, Integer uGender, String uVerifyCode) {
        this.uName = uName;
        this.uUsername = uUsername;
        this.uPassword = uPassword;
        this.uEmail = uEmail;
        this.uPhone = uPhone;
        this.uGender = uGender;
        this.uVerifyCode = uVerifyCode;
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

    @JsonProperty("uVerifyCode")
    private String uVerifyCode;

}
