package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.param.PageParams;
import com.qmx.smedicinebox.sys.entity.DailyMedicationPlanEntity;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.DailyMedicationPlanModifyVo;
import com.qmx.smedicinebox.vo.DailyMedicationPlanSaveVo;
import com.qmx.smedicinebox.vo.UserDailyPlanVO;


import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-11-07 21:27:10
 */
public interface DailyMedicationPlanService extends IService<DailyMedicationPlanEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(PageParams params);

    Boolean save(DailyMedicationPlanSaveVo dailyMedicationPlanSaveVo);

    Boolean updateById(DailyMedicationPlanModifyVo dailyMedicationPlan);

    R returnDailyMedicationPlan(String uIdentityId);

    R updateById(String userIdentityId, Integer mId);

    List<UserDailyPlanVO> selectUserPlanById(Integer userEntityId);
}

