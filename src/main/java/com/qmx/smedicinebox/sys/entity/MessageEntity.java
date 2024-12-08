package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.qmx.smedicinebox.valid.ListValue;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 消息
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
@Data
@TableName("smb_message")
@Validated
//@Component
public class MessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Integer ISREAD = 1;

	public MessageEntity(Date msgDateTime, Integer msgIsRead, Integer msgSender, Integer msgRecipient, String msgContent) {
		this.msgDateTime = msgDateTime;
		this.msgIsRead = msgIsRead;
		this.msgSender = msgSender;
		this.msgRecipient = msgRecipient;
		this.msgContent = msgContent;
	}

	public MessageEntity() {

	}

	public static final Integer NOTREAD = 0;
	/**
	 * 
	 */
	@TableId
	private Integer msgId;
	/**
	 * 时间
	 */
	private Date msgDateTime;
	/**
	 * 是否已读
	 */
	@ListValue(vals = {0,1})
	private Integer msgIsRead;
	/**
	 * 发送方ID
	 */
	private Integer msgSender;
	/**
	 * 接收方
	 */
	private Integer msgRecipient;
	/**
	 * 发送内容
	 */
	private String msgContent;

}
