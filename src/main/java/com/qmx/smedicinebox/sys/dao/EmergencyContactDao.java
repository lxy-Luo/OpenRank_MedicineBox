package com.qmx.smedicinebox.sys.dao;

import com.qmx.smedicinebox.sys.entity.EmergencyContactEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 紧急联系人
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 13:49:30
 */
@Mapper
public interface EmergencyContactDao extends BaseMapper<EmergencyContactEntity> {

    void baseInsert(@Param("ecEntity")EmergencyContactEntity emergencyContactEntity);
}
