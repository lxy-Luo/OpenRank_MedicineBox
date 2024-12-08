package com.qmx.smedicinebox.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MedicineHistory implements Serializable {
    private LocalDateTime sTime;
    private String mName;
    private String sDosing;
    private String sUnit;
    private String sPic;
    private String sId;
}
