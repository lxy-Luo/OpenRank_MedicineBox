package com.qmx.smedicinebox.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DailyMedicationPlanModifyVo {
    /**
     * 用药计量
     */
    @JsonProperty("dpDosage")
    private Integer dpDosage;

    /**
     * 用户id
     */
    @JsonProperty("dpUser")
    private Integer dpUser;

    /**
     * 药品id
     */
    @JsonProperty("dpMedicine")
    private Integer dpMedicine;

    /**
     * 每日用药计划id
     */
    @JsonProperty("dpId")
    private Integer dpId;
}
