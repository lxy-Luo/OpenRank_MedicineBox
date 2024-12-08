package com.qmx.smedicinebox.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginVo {

    @JsonProperty("uId")
    private Integer uId;

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

    @JsonProperty("uAvatar")
    private String uAvatar;
}
