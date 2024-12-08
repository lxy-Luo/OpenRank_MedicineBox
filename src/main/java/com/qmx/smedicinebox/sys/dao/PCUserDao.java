package com.qmx.smedicinebox.sys.dao;


import com.qmx.smedicinebox.sys.entity.PCUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PCUserDao {

    void insert(PCUser pcUser);

    PCUser selectByUsername(String username);

    void deleteById(Integer id);

    void updateById(PCUser pcUser);

    PCUser selectById(Integer id);

    List<PCUser> selectAll(PCUser pcUser);

}
