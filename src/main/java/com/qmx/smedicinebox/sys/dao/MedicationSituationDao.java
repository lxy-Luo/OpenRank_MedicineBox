package com.qmx.smedicinebox.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmx.smedicinebox.sys.entity.MedicationSituationEntity;
import com.qmx.smedicinebox.vo.MedicineHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用药情况表
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
@Mapper
public interface MedicationSituationDao extends BaseMapper<MedicationSituationEntity> {

    int insertMs(@Param("mSEntity") MedicationSituationEntity mSEntity);

    int baseInsert(@Param("medicationSituationEntity")  MedicationSituationEntity medicationSituationEntity);

    List<MedicineHistory> getHistoryByUserId(Integer userId,Integer page);
}
