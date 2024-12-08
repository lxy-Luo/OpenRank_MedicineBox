package com.qmx.smedicinebox.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityInfoVO {
    private Integer uId;
    //名字
    private String uName;
    //用户名
    private String uUsername;
    //邮件
    private String uEmail;
    //手机号
    private String uPhone;
    //性别
    private Integer uGender;
    //用户头像
    private String  uAvatar;
    //紧急联系人名称
    private String EmergencyContactName;
    //紧急联系人名称
    private String EmergencyContactPhone;

}
