package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import com.qmx.smedicinebox.valid.LoginGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
@TableName("smb_user")
@Component
@Validated
@Data
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String VALID="Y";
	public static String INVALID="N";
	public static Integer VERIFYCODE_TIME_LIMIT=60*5;
	public static String DEFAULT_PASSWORD="123456";

	/**
	 * 用户id
	 */
	@TableId
	@TableField("u_id")
	private Integer uId;
	/**
	 * 用户姓名
	 */
	private String uName;
	/**
	 * 用户账号
	 */
	@NotEmpty(groups = LoginGroup.class)
	private String uUsername;
	/**
	 * 用户密码
	 */
	@NotEmpty(groups = LoginGroup.class)
	private String uPassword;

	private String uEmail;

	private String uPhone;

	private Integer uGender;

	private String uVerifyCode;

	private Date uVerifyCodeTime;

	private Date uRegistTime;

	private String  uAvatar;

	private String uIsValid;

	private Integer uEmergencyContact;

	private String uIdentityId;
}
