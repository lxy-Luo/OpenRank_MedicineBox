package com.qmx.smedicinebox.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
    int baseInsert(@Param("usEntity")UserEntity user);
}
