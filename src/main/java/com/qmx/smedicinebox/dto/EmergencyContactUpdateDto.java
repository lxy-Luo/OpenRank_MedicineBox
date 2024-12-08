package com.qmx.smedicinebox.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmergencyContactUpdateDto {


    public EmergencyContactUpdateDto(Integer ecId, String ecName, String ecPhone, String ecEmail, Integer ecGender, String ecDesc) {
        this.ecId = ecId;
        this.ecName = ecName;
        this.ecPhone = ecPhone;
        this.ecEmail = ecEmail;
        this.ecGender = ecGender;
        this.ecDesc = ecDesc;
    }

    @JsonProperty("ecId")
    private Integer ecId;

    @JsonProperty("ecName")
    private String ecName;

    @JsonProperty("ecPhone")
    private String ecPhone;

    @JsonProperty("ecEmail")
    private String ecEmail;

    @JsonProperty("ecGender")
    private Integer ecGender;

    @JsonProperty("ecDesc")
    private String ecDesc;

}
