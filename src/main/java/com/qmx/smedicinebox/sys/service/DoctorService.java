package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.sys.entity.DoctorEntity;

import java.util.Map;

/**
 * 
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 06:34:29
 */
public interface DoctorService extends IService<DoctorEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

