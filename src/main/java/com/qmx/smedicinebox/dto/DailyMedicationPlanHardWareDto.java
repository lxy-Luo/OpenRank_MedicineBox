package com.qmx.smedicinebox.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DailyMedicationPlanHardWareDto {


    public DailyMedicationPlanHardWareDto(Integer dpDosage, String mName, String mDesc,Integer dpStatus) {
        this.dpDosage = dpDosage;
        this.mName = mName;
        this.mDesc = mDesc;
        this.dpStatus = dpStatus;
    }

    public DailyMedicationPlanHardWareDto() {
    }

    /**
     * 用药计量
     */
    private Integer dpDosage;

    /**
     * 药品名称
     */
    private String mName;
    /**
     * 药品说明
     */
    private String mDesc;

    /**
     * 状态 0 未完成 1 已完成
     */
    private Integer dpStatus;

}
