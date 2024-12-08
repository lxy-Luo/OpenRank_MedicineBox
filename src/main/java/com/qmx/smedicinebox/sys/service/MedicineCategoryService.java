package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.sys.entity.MedicineCategoryEntity;
import com.qmx.smedicinebox.utils.R;

import java.util.Map;

/**
 * 
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
public interface MedicineCategoryService extends IService<MedicineCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R listAll();

    R deleteAll();
}

