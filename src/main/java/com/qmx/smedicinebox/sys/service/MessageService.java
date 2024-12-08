package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.sys.entity.MessageEntity;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.MessageList;
import com.qmx.smedicinebox.vo.MessageSaveVo;

import java.util.Map;

/**
 * 消息
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
public interface MessageService extends IService<MessageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    MessageList listDetails();

    R updateIsRead(Integer msgId, Integer isRead);

    R updateIsReadAll(Integer userId);

    R deleteAll(Integer userId);

    void save(MessageSaveVo message);

    Integer getUnReadTotal(Integer userId);

    boolean baseSave(MessageEntity messageEntity);

    void remindMedication(String noon);

    MessageList listDetails(Integer userId);

    R getListByUserId(Integer userId, Integer page);
}

