package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 设备
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@Data
@TableName("smb_device")
public class DeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer dId;
	/**
	 * 设备mi
	 */
	private String dName;
	/**
	 * 设备lei
	 */
	private String dType;
	/**
	 * s
	 */
	private String dModel;

}
