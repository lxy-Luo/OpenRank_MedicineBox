package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户每日剂量bi
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 08:02:00
 */
@Data
@TableName("smb_user_daily_dosage")
public class UserDailyDosageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer ddUser;
	/**
	 * 
	 */
	private Integer ddTotalDosage;
	/**
	 * 
	 */
	private String ddDosageUnit;
	/**
	 * 
	 */
	private Integer ddId;
	/**
	 * 用药频率
	 */
	private Integer ddFrequency;

}
