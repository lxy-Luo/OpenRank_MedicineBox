package com.qmx.smedicinebox.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmx.smedicinebox.sys.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface AdminDao {
    /**
     * 新增
     */
    int insert(Admin admin);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Admin admin);

    /**
     * 根据ID查询
     */
    Admin selectById(Integer id);

    /**
     * 查询所有
     */
    List<Admin> selectAll(Admin admin);

    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);
}
