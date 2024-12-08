package com.qmx.smedicinebox.vo;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class MedicationPictureVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片id
	 */
	private Integer pId;
	/**
	 * 图片上传日期
	 */
	private String pDate;

	/**
	 * 图片上传日期
	 */
	private String pTime;

	/**
	 * 图片路径
	 */
	private String pFilepath;
	/**
	 * 图片拍摄的设备
	 */
	private Integer  pDevice;

}
