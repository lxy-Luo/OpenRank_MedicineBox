package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.sys.entity.MedicationPictureEntity;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;

import java.io.IOException;
import java.util.Map;

/**
 * 用药图片
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
public interface MedicationPictureService extends IService<MedicationPictureEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R getImgBin(String bindData);
}

