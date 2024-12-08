package com.qmx.smedicinebox.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;

import com.qmx.smedicinebox.sys.dao.SenderDao;
import com.qmx.smedicinebox.sys.entity.SenderEntity;
import com.qmx.smedicinebox.sys.service.SenderService;


@Service("senderService")
public class SenderServiceImpl extends ServiceImpl<SenderDao, SenderEntity> implements SenderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SenderEntity> page = this.page(
                new Query<SenderEntity>().getPage(params),
                new QueryWrapper<SenderEntity>()
        );

        return new PageUtils(page);
    }

}