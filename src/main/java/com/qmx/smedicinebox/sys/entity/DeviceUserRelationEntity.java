package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 设备与用户的关联
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@Data
@TableName("smb_device_user_relation")
public class DeviceUserRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer durId;
	/**
	 * 设备id
	 */
	private Integer durDevice;
	/**
	 * yo
	 */
	private Integer durUser;

}
