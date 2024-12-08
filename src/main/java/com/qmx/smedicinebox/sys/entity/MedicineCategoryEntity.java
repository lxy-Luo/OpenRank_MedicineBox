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
 * @date 2024-03-27 09:29:58
 */
@Data
@TableName("smb_medicine_category")
public class MedicineCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId
	private Integer cId;
	/**
	 * 分类名
	 */
	private String cName;

}
