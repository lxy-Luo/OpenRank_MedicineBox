package com.qmx.smedicinebox.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MedicationSituationVo implements Serializable {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("drugId")
	private Integer drugId;

	@JsonProperty("date")
	private String date;

	@JsonProperty("time")
	private String time;

	@JsonProperty("all_number")
	private Integer all_number;

	@JsonProperty("unit")
	private String unit;

	@JsonProperty("image")
	private String image;

	@JsonProperty("userId")
	private Integer userId;

}
