package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.sys.entity.DeviceUserRelationEntity;
import com.qmx.smedicinebox.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 设备与用户的关联
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
public interface DeviceUserRelationService extends IService<DeviceUserRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer getUserId(Integer deviceId);

    List<Integer> getUserIds(Integer deviceId);

    R getPictures(Integer userId, String date);

    Integer getDeviceId(Integer userId);
}

