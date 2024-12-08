package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.sys.entity.DeviceEntity;

import java.util.Map;

/**
 * 设备
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
public interface DeviceService extends IService<DeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageInfo<DeviceEntity> selectPage(DeviceEntity deviceEntity, Integer pageNum, Integer pageSize);
}

