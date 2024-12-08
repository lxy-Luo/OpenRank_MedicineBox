package com.qmx.smedicinebox.vo;

/*
* 用药计划单项vo
* */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDailyPlanVO {
    private Integer dosage; //药品剂量
    private String medicationName; //药品名称
    private String medicationImage; //药品类型
    private String medicationIntroduction; //药品简介
    private String medicationUnit; //药品单位  粒，包，瓶
    private String medicationType; //药品类型  如：中药，抗生素
}
