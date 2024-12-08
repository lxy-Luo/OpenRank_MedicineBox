package com.qmx.smedicinebox.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MedicationSituationModifyVo {

    @JsonProperty("DrugId")
    private Integer DrugId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private String time;

    @JsonProperty("All_number")
    private Integer All_number;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("image")
    private String image;

    @JsonProperty("userId")
    private Integer userId;
}
