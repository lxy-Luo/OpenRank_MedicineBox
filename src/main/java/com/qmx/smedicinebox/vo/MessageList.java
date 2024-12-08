package com.qmx.smedicinebox.vo;

import com.qmx.smedicinebox.sys.entity.MessageEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 消息
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
@Data
public class MessageList implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<MessageVo> messages;

	private Integer unreadTotal;

	private Integer Total;

}
