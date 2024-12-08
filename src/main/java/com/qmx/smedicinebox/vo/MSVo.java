package com.qmx.smedicinebox.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
public class MSVo {
    @NotEmpty
    @JsonProperty("date")
    private String date;

    @NotEmpty
    @JsonProperty("userId")
    private Integer userId;
}
