package com.qmx.smedicinebox.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmx.smedicinebox.sys.entity.MedicationPictureEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用药图片
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
@Mapper
public interface MedicationPictureDao extends BaseMapper<MedicationPictureEntity> {

    int baseInsert(@Param("medicationPictureEntity") MedicationPictureEntity medicationPictureEntity);
}
