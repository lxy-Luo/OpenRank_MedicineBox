package com.qmx.smedicinebox.sys.service.impl;

import com.qmx.smedicinebox.dto.EmergencyContactUpdateDto;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;

import com.qmx.smedicinebox.sys.dao.EmergencyContactDao;
import com.qmx.smedicinebox.sys.entity.EmergencyContactEntity;
import com.qmx.smedicinebox.sys.service.EmergencyContactService;


@Service("emergencyContactService")
public class EmergencyContactServiceImpl extends ServiceImpl<EmergencyContactDao, EmergencyContactEntity> implements EmergencyContactService {

    private final EmergencyContactDao emergencyContactDao;

    public EmergencyContactServiceImpl(EmergencyContactDao emergencyContactDao) {
        this.emergencyContactDao = emergencyContactDao;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EmergencyContactEntity> page = this.page(
                new Query<EmergencyContactEntity>().getPage(params),
                new QueryWrapper<EmergencyContactEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public EmergencyContactEntity saveDefault(Integer uId) {
        EmergencyContactEntity emergencyContactEntity = new EmergencyContactEntity();
        emergencyContactEntity.setEcUser(uId);
        emergencyContactEntity.setEcIsValid(EmergencyContactEntity.UNVALID);
        this.baseMapper.baseInsert(emergencyContactEntity);
        return emergencyContactEntity;
    }

    @Override
    public void updatePart(EmergencyContactUpdateDto emergencyContactUpdateDto) {
        Integer ecId = emergencyContactUpdateDto.getEcId();

        EmergencyContactEntity emergencyContactEntity = this.baseMapper.selectById(ecId);

        if(Objects.nonNull(emergencyContactUpdateDto.getEcName()) && !emergencyContactUpdateDto.getEcName().trim().isEmpty()){
            String ecName = emergencyContactUpdateDto.getEcName();
            emergencyContactEntity.setEcName(ecName);
        }
        if(Objects.nonNull(emergencyContactUpdateDto.getEcPhone()) && !emergencyContactUpdateDto.getEcPhone().trim().isEmpty()){
            String ecPhone = emergencyContactUpdateDto.getEcPhone();
            emergencyContactEntity.setEcPhone(ecPhone);
        }
        if(Objects.nonNull(emergencyContactUpdateDto.getEcEmail()) && !emergencyContactUpdateDto.getEcEmail().trim().isEmpty()){
            String ecEmail = emergencyContactUpdateDto.getEcEmail();
            emergencyContactEntity.setEcEmail(ecEmail);
        }
        if(Objects.nonNull(emergencyContactUpdateDto.getEcGender())){
            Integer ecGender = emergencyContactUpdateDto.getEcGender();
            emergencyContactEntity.setEcGender(ecGender);
        }
        if(Objects.nonNull(emergencyContactUpdateDto.getEcDesc()) && !emergencyContactUpdateDto.getEcDesc().trim().isEmpty()){
            String ecDesc = emergencyContactUpdateDto.getEcDesc();
            emergencyContactEntity.setEcDesc(ecDesc);
        }

        emergencyContactEntity.setEcIsValid(EmergencyContactEntity.ISVALID);

        this.baseMapper.updateById(emergencyContactEntity);
    }

    @Override
    public void baseInsert(EmergencyContactEntity emergencyContact) {
        this.baseMapper.baseInsert(emergencyContact);
    }

    @Override
    public EmergencyContactEntity selectById(Integer emergencyContactID) {
        return emergencyContactDao.selectById(emergencyContactID);
    }

}