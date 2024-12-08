package com.qmx.smedicinebox.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmx.smedicinebox.sys.entity.DailyMedicationPlanEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-11-07 21:27:10
 */
@Mapper
public interface DailyMedicationPlanDao extends BaseMapper<DailyMedicationPlanEntity> {
    int insertDailyMedicationPlan(@Param("dailyMedicationPlan") DailyMedicationPlanEntity dailyMedicationPlan);
}
