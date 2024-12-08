package com.qmx.smedicinebox.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class MessageVo {
    /**
     *
     */
    @TableId
    private Integer id;
    /**
     * 日期
     */
    private String date;
    /**
     * 时间
     */
    private String time;
    /**
     *
     */
    private Integer isRead;
    /**
     * 发送方
     */
    private String sender;
    /**
     * 发送内容
     */
    private String content;
}
