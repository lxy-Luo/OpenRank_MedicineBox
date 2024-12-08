package com.qmx.smedicinebox;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.dto.AnalysisDto;
import com.qmx.smedicinebox.sys.dao.*;
import com.qmx.smedicinebox.sys.entity.*;
import com.qmx.smedicinebox.utils.QWenPlusUtil;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@SpringBootTest
public class QWenPlusUtilTest {

    @Autowired
    private DailyMedicationPlanDao dailyMedicationPlanDao;
    @Autowired
    private MedicationSituationDao medicationSituationDao;

    @Autowired
    private MedicineDao medicineDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;
    @Test
    public void executeWeeklyTask() {

        List<UserEntity> userEntities = userDao.selectList(null);
        for (UserEntity userEntity : userEntities) {
            // 发送邮件提醒用户
            String text = analysis(userEntity);
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMsgContent(text);
            messageEntity.setMsgRecipient(userEntity.getUId());
            messageEntity.setMsgSender(Constant.SENDER_APP);
            messageEntity.setMsgIsRead(Constant.MSG_NOT_READ);
            messageEntity.setMsgDateTime(new Date());
            messageDao.insert(messageEntity);
        }

    }

    public String analysis (UserEntity userEntity){
        QWenPlusUtil qWenPlusUtil = new QWenPlusUtil();
        // 获取当前日期
        LocalDate now = LocalDate.now();
        // 计算一周前的日期
        LocalDate oneWeekAgo = now.minusDays(7);

        Date startDate = Date.from(oneWeekAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<MedicationSituationEntity> sUser = medicationSituationDao.
                selectList(new QueryWrapper<MedicationSituationEntity>()
                        .eq("s_user", userEntity.getUId())
                        .between("s_date",startDate,endDate));
        List<DailyMedicationPlanEntity> dpUser = dailyMedicationPlanDao.
                selectList(new QueryWrapper<DailyMedicationPlanEntity>()
                        .eq("dp_user", userEntity.getUId()));

        // 创建两个 HashMap 并添加数据
        Map<String, List<DailyMedicationPlanEntity>> planMap = new HashMap<>();
        planMap.put("用药计划", dpUser);

        Map<String, List<MedicationSituationEntity>> situationMap = new HashMap<>();
        situationMap.put("用药情况", sUser);

        Map<Integer, String> integerStringArrayList = new HashMap<>();
        List<MedicineEntity> medicineEntities = medicineDao.selectList(null);
        ArrayList<Integer> arrrayList = new ArrayList<>();

        for(DailyMedicationPlanEntity mp:dpUser){
            arrrayList.add(mp.getDpMedicine());
        }
        for(MedicationSituationEntity mp:sUser){
            arrrayList.add(mp.getSMedicine());
        }

        for(MedicineEntity mp:medicineEntities){
            if(!arrrayList.contains(mp.getMId())){
                continue;
            }
            integerStringArrayList.put(mp.getMId(),mp.getMName());
        }

        HashMap<String, Map<Integer, String>> stringMapHashMap = new HashMap<>();
        stringMapHashMap.put("药品id对应的药品名称",integerStringArrayList);

        AnalysisDto analysisDto = new AnalysisDto(
                planMap,
                situationMap,
                stringMapHashMap
        );
        String chat = qWenPlusUtil.chat("请根据一下信息分析我的数据，并给出意见，不要出现药品id，只给我显示药品名称" + analysisDto.toString());

        return chat;
    }
}
