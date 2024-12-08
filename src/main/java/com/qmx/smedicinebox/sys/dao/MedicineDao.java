package com.qmx.smedicinebox.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 药品
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
@Mapper
public interface MedicineDao extends BaseMapper<MedicineEntity> {

    List<MedicineEntity> selectAll(MedicineEntity medicineEntity);
}
