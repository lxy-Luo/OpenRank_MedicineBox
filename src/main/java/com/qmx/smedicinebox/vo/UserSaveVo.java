package com.qmx.smedicinebox.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qmx.smedicinebox.valid.LoginGroup;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class UserSaveVo {
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
