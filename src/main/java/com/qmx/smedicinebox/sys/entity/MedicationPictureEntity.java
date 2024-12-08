package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用药图片
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
@Data
@TableName("smb_medication_picture")
public class MedicationPictureEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片id
	 */
	@TableId
	private Integer pId;
	/**
	 * 图片上传日期
	 */
	private Date pDate;
	/**
	 * 图片路径
	 */
	private String pFilepath;
	/**
	 * 图片拍摄的设备
	 */
	private Integer pDevice;

}
