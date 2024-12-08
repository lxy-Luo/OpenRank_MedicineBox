package com.qmx.smedicinebox.sys.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 用户类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PCUser extends Account{
    private Integer id;
    //用户名
    private String username;
    //密码
    private String password;
    //真实姓名
    private String name;
    //头像
    private String avatar;
    //角色
    private String role;
    //性别
    private String sex;
    //电话
    private String phone;
    //邮件
    private String email;
    //简介
    private String info;
    //绑定设备id
    private Integer device;

}
