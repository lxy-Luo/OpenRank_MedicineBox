package com.qmx.smedicinebox.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@Data
public class MedicineCategoryAllList implements Serializable {
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

	private List<MedicineEntity> Medicines;

}
