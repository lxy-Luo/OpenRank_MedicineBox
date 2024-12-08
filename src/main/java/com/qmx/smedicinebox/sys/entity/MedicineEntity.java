package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 药品
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
@Data
@TableName("smb_medicine")
public class MedicineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 药品id
	 */
	@TableId
	private Integer mId;
	/**
	 * 药品名称
	 */
	private String mName;
	/**
	 * y
	 */
	private Integer mCategory;
	/**
	 * 药品说明
	 */
	private String mDesc;
	/**
	 * 药品图片
	 */
	private String mPic;
	/**
	 * 药品名称
	 */
	private String mCname;

	/**
	 * 药品剂量单位
	 */
	private String mUnit;
	/**
	 * 药品厂商
	 */
	private String mManufacturer;

}
