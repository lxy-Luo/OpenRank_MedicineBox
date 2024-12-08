package com.qmx.smedicinebox.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.sys.dao.MedicationPictureDao;
import com.qmx.smedicinebox.sys.entity.MedicationPictureEntity;
import com.qmx.smedicinebox.utils.DateTimeUtil;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.MedicationPictureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;

import com.qmx.smedicinebox.sys.dao.DeviceUserRelationDao;
import com.qmx.smedicinebox.sys.entity.DeviceUserRelationEntity;
import com.qmx.smedicinebox.sys.service.DeviceUserRelationService;


@Service("deviceUserRelationService")
public class DeviceUserRelationServiceImpl extends ServiceImpl<DeviceUserRelationDao, DeviceUserRelationEntity> implements DeviceUserRelationService {
    @Autowired
    private MedicationPictureDao medicationPictureDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DeviceUserRelationEntity> page = this.page(
                new Query<DeviceUserRelationEntity>().getPage(params),
                new QueryWrapper<DeviceUserRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Integer getUserId(Integer deviceId) {
        QueryWrapper<DeviceUserRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("dur_device",deviceId);
        DeviceUserRelationEntity deviceUserRelationEntity = this.baseMapper.selectOne(wrapper);
        Integer durUser = deviceUserRelationEntity.getDurUser();
        return durUser;
    }

    @Override
    public List<Integer> getUserIds(Integer deviceId) {
        QueryWrapper<DeviceUserRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("dur_device",deviceId);
        List<DeviceUserRelationEntity> deviceUserRelationEntities = this.baseMapper.selectList(wrapper);
        List<Integer> userList = deviceUserRelationEntities.stream().map(item -> {
            Integer durUser = item.getDurUser();
            return durUser;
        }).collect(Collectors.toList());
        return userList;
    }


    @Override
    public R getPictures(Integer userId,String date) {

        /**
         * 查找用户关联的设备
         */
        QueryWrapper<DeviceUserRelationEntity> wrappers = Wrappers.query();
        wrappers.eq("dur_user", userId);
        List<DeviceUserRelationEntity> deviceUserRelationEntities = this.baseMapper.selectList(wrappers);

        /**
         * 查找设备对应的picture
         */
        List<MedicationPictureEntity> medicationPictureEntities = new ArrayList<>();
        deviceUserRelationEntities.forEach(item -> {
            Integer durDevice = item.getDurDevice();
            QueryWrapper<MedicationPictureEntity> Pwrappers = Wrappers.query();
            Pwrappers.eq("p_device", durDevice);
            List<MedicationPictureEntity> medicationPictureEntities1 = medicationPictureDao.selectList(Pwrappers);

            medicationPictureEntities.addAll(medicationPictureEntities1);
        });

        /**
         * 过滤相应日期的picture，在上面使用Pwrappers查找也可以
         * 封装Vo对象
         */
        List<MedicationPictureVo> medicationPictureVoCollect = medicationPictureEntities.stream()
                .filter((item)->{
                    String s = DateTimeUtil.splitDate(item.getPDate(), Constant.HYPHEN);
                    return s.equals(date);
                })
                .map(item -> {

            MedicationPictureVo medicationPictureVo = new MedicationPictureVo();
            medicationPictureVo.setPId(item.getPId());
            medicationPictureVo.setPDevice(item.getPDevice());
            medicationPictureVo.setPFilepath(item.getPFilepath());
            medicationPictureVo.setPDate(DateTimeUtil.splitDate(item.getPDate(),Constant.HYPHEN));
            medicationPictureVo.setPTime(DateTimeUtil.splitTime(item.getPDate(),Constant.COLON));

            return medicationPictureVo;
        }).collect(Collectors.toList());

        return R.ok().put("pictures",medicationPictureVoCollect);
    }

    @Override
    public Integer getDeviceId(Integer userId) {
        QueryWrapper<DeviceUserRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("dur_user",userId);
        DeviceUserRelationEntity deviceUserRelationEntity = this.baseMapper.selectOne(wrapper);
        Integer durDevice = deviceUserRelationEntity.getDurDevice();
        return durDevice;
    }

}