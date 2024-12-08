package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.sys.entity.MedicationSituationEntity;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.MedicationSituationModifyVo;
import com.qmx.smedicinebox.vo.MedicationSituationVo;
import jakarta.servlet.http.HttpSession;
import org.apache.iotdb.session.Session;

import java.util.List;
import java.util.Map;

/**
 * 用药情况表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
public interface MedicationSituationService extends IService<MedicationSituationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R medicationSituationList(Integer userId, String date);

    R save(MedicationSituationVo mSVo, HttpSession session);

    R save(MedicationSituationVo medicationSituationVo);

    void insertNewRecord(Integer integer, String link, MedicineEntity medicine, String userIdentityId);


    boolean determineSave(Integer medicineId);

    void updateById(MedicationSituationModifyVo medicationSituation);

    R getHistory(Integer userId,Integer page);
    //IoT数据库根据日期查找数据
    R getDataByDate(Session session, Integer userId, String date);

    //IoT数据库插入单条数据接口
    R insertRecord(Session session, Integer userId, List<Object> values);

    //IoT数据库插入多条数据接口
    R insertRecords(Session session,List<Integer> uIds,List<List<Object>> valueList);

}

