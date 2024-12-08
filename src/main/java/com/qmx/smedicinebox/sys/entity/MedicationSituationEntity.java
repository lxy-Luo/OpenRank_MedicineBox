package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用药情况表
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
@Data
@TableName("smb_medication_situation")
public class MedicationSituationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "MedicationSituationEntity{" +
				"sId=" + sId +
				", sMedicine=" + sMedicine +
				", sDate=" + sDate +
				", sTime=" + sTime +
				", sDosing=" + sDosing +
				", sUnit='" + sUnit + '\'' +
				", sPic='" + sPic + '\'' +
				", sUser=" + sUser +
				'}';
	}

	/**
	 * 用药情况id
	 */
	@TableId
	private Integer sId;
	/**
	 * 药品id
	 */
	private Integer sMedicine;
	/**
	 * 用药日期
	 */
	private Date sDate;
	/**
	 * 用药时间
	 */
	private Date sTime;
	/**
	 * 用药剂量
	 */
	private Integer sDosing;
	/**
	 * 剂量单位
	 */
	private String sUnit;

	/**
	 * 拿药图片
	 */
	private String sPic;
	/**
	 * 用户id
	 */
	private Integer sUser;

}
