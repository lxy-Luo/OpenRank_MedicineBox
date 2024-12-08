package com.qmx.smedicinebox.sys.dao;

import com.qmx.smedicinebox.sys.entity.DailyMedicationPlanEntity;
import com.qmx.smedicinebox.sys.entity.DeviceUserRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 设备与用户的关联
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@Mapper
public interface DeviceUserRelationDao extends BaseMapper<DeviceUserRelationEntity> {
    int insertDeviceUserRelation(@Param("deviceUserRelation") DeviceUserRelationEntity deviceUserRelationEntity);

}
