package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 06:34:29
 */
@Data
@TableName("smb_doctor")
public class DoctorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 医生id
	 */
	@TableId
	private Integer dId;

	/**
	 * 医生姓名
	 */
	private String dName;

	/**
	 * 医生性别（0表示男性，1表示女性）
	 */
	private Integer dGender;

	/**
	 * 医生出生日期
	 */
	private Date dDateOfBirth;

	/**
	 * 医生所在科室
	 */
	private String dDepartment;

	/**
	 * 医生电子邮箱
	 */
	private String dEmail;

	/**
	 * 医生电话号码
	 */
	private String dPhone;

	/**
	 * 医生联系地址
	 */
	private String dAddress;

	/**
	 * 医生资格证书或学历
	 */
	private String dQualification;

	/**
	 * 医生专业领域
	 */
	private String dSpecialization;

	/**
	 * 医生工作经验年限
	 */
	private Integer dExperienceYears;

	/**
	 * 医生加入医院或诊所的日期
	 */
	private Date dJoinDate;


}
