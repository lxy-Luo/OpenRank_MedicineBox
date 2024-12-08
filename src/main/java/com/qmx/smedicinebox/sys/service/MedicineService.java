package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 药品
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
public interface MedicineService extends IService<MedicineEntity> {

    PageUtils queryPage(Map<String, Object> params);

    MedicineEntity equalMedicineName(List<String> s);

    PageInfo<MedicineEntity> selectPage(MedicineEntity medicineEntity, Integer pageNum, Integer pageSize);

}

