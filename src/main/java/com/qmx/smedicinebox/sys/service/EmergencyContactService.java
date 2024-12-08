package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.dto.EmergencyContactUpdateDto;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.sys.entity.EmergencyContactEntity;

import java.util.Map;

/**
 * 紧急联系人
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 13:49:30
 */
public interface EmergencyContactService extends IService<EmergencyContactEntity> {

    PageUtils queryPage(Map<String, Object> params);

    EmergencyContactEntity saveDefault(Integer uId);

    void updatePart(EmergencyContactUpdateDto emergencyContactUpdateDto);

    void baseInsert(EmergencyContactEntity emergencyContact);

     EmergencyContactEntity selectById(Integer emergencyContactID);
}

