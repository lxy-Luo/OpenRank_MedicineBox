package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.sys.entity.UserDailyDosageEntity;

import java.util.Map;

/**
 * 用户每日剂量bi
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 08:02:00
 */
public interface UserDailyDosageService extends IService<UserDailyDosageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDefault(Integer uId);
}

