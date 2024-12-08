package com.qmx.smedicinebox.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;

import com.qmx.smedicinebox.sys.dao.DeviceDao;
import com.qmx.smedicinebox.sys.entity.DeviceEntity;
import com.qmx.smedicinebox.sys.service.DeviceService;


@Service("deviceService")
public class DeviceServiceImpl extends ServiceImpl<DeviceDao, DeviceEntity> implements DeviceService {

    @Resource
    private DeviceDao deviceDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DeviceEntity> page = this.page(
                new Query<DeviceEntity>().getPage(params),
                new QueryWrapper<DeviceEntity>()
        );

        return new PageUtils(page);
    }

    public List<DeviceEntity> selectDeviceById(Integer id) {
        return baseMapper.selectList(new QueryWrapper<DeviceEntity>().eq("id", id));
    }
    @Override
    public PageInfo<DeviceEntity> selectPage(DeviceEntity deviceEntity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<DeviceEntity> wrapper = new LambdaQueryWrapper<>();
        if(deviceEntity.getDId()!=null){
            wrapper.like(DeviceEntity::getDId,deviceEntity.getDId());
        }
        List<DeviceEntity> list = deviceDao.selectList(wrapper);
        return PageInfo.of(list);
    }

}