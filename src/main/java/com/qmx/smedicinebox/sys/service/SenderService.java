package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.qmx.smedicinebox.sys.entity.SenderEntity;
import com.qmx.smedicinebox.utils.PageUtils;

import java.util.Map;

/**
 * 发送方
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
public interface SenderService extends IService<SenderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

