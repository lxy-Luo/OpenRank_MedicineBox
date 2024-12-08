package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 紧急联系人
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 13:49:30
 */
@Data
@TableName("smb_emergency_contact")
public class EmergencyContactEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Integer ISVALID = 1;
	public static final Integer UNVALID = 0;

	/**
	 * 
	 */
	@TableId
	private Integer ecId;
	/**
	 * 姓名
	 */
	private String ecName;
	/**
	 * 电话
	 */
	private String ecPhone;
	/**
	 * you'x
	 */
	private String ecEmail;
	/**
	 * 
	 */
	private Integer ecGender;
	/**
	 * 
	 */
	private String ecDesc;
	/**
	 * 关联用户
	 */
	private Integer ecUser;


	private Integer ecIsValid;

}
