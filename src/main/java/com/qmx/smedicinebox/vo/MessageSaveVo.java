package com.qmx.smedicinebox.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageSaveVo {
    /**
     * 发送方
     */
    @JsonProperty("sender")
    private Integer sender;
    /**
     * 接受方
     */
    @JsonProperty("userId")
    private Integer userId;
    /**
     * 发送内容
     */
    @JsonProperty("content")
    private String content;
}
