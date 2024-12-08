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
 * @date 2024-11-07 21:27:10
 */
@Data
@TableName("smb_daily_medication_plan")
public class DailyMedicationPlanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "DailyMedicationPlanEntity{" +
				"dpUser=" + dpUser +
				", dpId=" + dpId +
				", dpDosage=" + dpDosage +
				", dpMedicine=" + dpMedicine +
				", dpStatus=" + dpStatus +
				'}';
	}

	public static final int STATUS_COMPLETE = 1;
	public static final int STATUS_NOT_COMPLETE = 0;

	public DailyMedicationPlanEntity(Integer dpUser, Integer dpDosage, Integer dpMedicine) {
		this.dpUser = dpUser;
		this.dpDosage = dpDosage;
		this.dpMedicine = dpMedicine;
	}

	public DailyMedicationPlanEntity() {
	}

	/**
	 * 用户id
	 */
	private Integer dpUser;
	/**
	 * 每日用药计划id
	 */
	@TableId
	private Integer dpId;
	/**
	 * 用药计量
	 */
	private Integer dpDosage;
	/**
	 * 药品id
	 */
	private Integer dpMedicine;
	/**
	 * 状态 0 未完成 1 已完成
	 */
	private Integer dpStatus;


}
