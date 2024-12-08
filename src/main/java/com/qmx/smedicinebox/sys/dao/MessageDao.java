package com.qmx.smedicinebox.sys.dao;

import com.qmx.smedicinebox.sys.entity.MessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.MessageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息
 * 
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {

    Integer updateBatchIds(@Param("messageEntities") List<Integer> messageEntities);

    Integer baseInsert(@Param("messageEntity") MessageEntity messageEntity);

    List<MessageVo> selectListByUserId(Integer userId, Integer page);
}
