package com.qmx.smedicinebox.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;

import com.qmx.smedicinebox.sys.dao.UserDailyDosageDao;
import com.qmx.smedicinebox.sys.entity.UserDailyDosageEntity;
import com.qmx.smedicinebox.sys.service.UserDailyDosageService;


@Service("userDailyDosageService")
public class UserDailyDosageServiceImpl extends ServiceImpl<UserDailyDosageDao, UserDailyDosageEntity> implements UserDailyDosageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserDailyDosageEntity> page = this.page(
                new Query<UserDailyDosageEntity>().getPage(params),
                new QueryWrapper<UserDailyDosageEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDefault(Integer uId) {
        UserDailyDosageEntity userDailyDosageEntity = new UserDailyDosageEntity();
        userDailyDosageEntity.setDdDosageUnit("粒");
        userDailyDosageEntity.setDdUser(uId);
        userDailyDosageEntity.setDdFrequency(3);
        userDailyDosageEntity.setDdTotalDosage(12);
        this.baseMapper.insert(userDailyDosageEntity);
    }

}