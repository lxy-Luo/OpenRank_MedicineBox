package com.qmx.smedicinebox.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.qmx.smedicinebox.valid.LoginGroup;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class PCUserInfoDto {


    private Integer uId;

    private String uName;

    private String uUsername;

    private String uPassword;

    private String uEmail;

    private String uPhone;

    private Integer uGender;

    private Date uRegistTime;

    private String  uAvatar;

    private Integer uEmergencyContact;

    private Boolean uAwaitingFaceScan;

}
