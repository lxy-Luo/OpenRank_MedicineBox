package com.qmx.smedicinebox.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 发送方
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
@Data
@TableName("smb_sender")
public class SenderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer sedId;
	/**
	 * 发送方名称
	 */
	private String sedName;
	/**
	 * 发送方介绍
	 */
	private String sedIntr;

}
