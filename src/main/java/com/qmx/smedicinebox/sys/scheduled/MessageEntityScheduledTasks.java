package com.qmx.smedicinebox.sys.scheduled;

import com.alibaba.dashscope.common.Message;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.dto.AnalysisDto;
import com.qmx.smedicinebox.sys.dao.*;
import com.qmx.smedicinebox.sys.entity.*;
import com.qmx.smedicinebox.sys.service.*;
import com.qmx.smedicinebox.sys.service.impl.MedicationSituationServiceImpl;
import com.qmx.smedicinebox.utils.DateTimeUtil;
import com.qmx.smedicinebox.utils.EmailUtil;
import com.qmx.smedicinebox.utils.QWenPlusUtil;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MessageEntityScheduledTasks {
    @Autowired
    private MedicationSituationService medicationSituationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDailyDosageService userDailyDosageService;

    @Autowired
    private MedicationSituationDao medicationSituationDao;


    @Autowired
    private DailyMedicationPlanDao dailyMedicationPlanDao;

    @Autowired
    private MedicineDao medicineDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;


    @Autowired
    private UserDao userDao;

    @Autowired
    private EmergencyContactService emergencyContactService;

    private static final Logger logger = LogManager.getLogger(MessageEntityScheduledTasks.class);

    @Autowired
    private MessageDao messageDao;

    //     每天凌晨零点执行
    @Scheduled(cron = "0 0 0 * * *") // 0秒 0分 0时 每天

//    // 每分钟执行一次
//    @Scheduled(cron = "0 * * * * *") // 每分钟

    // 每十秒执行一次
//    @Scheduled(cron = "*/10 * * * * *") // 每十秒
    public void generatePreviousDayMedicationRecord(){

        //获取昨天date
        Date date = new Date();
        Date yesterdayDate = DateTimeUtil.getYesterdayDate();
        String ydate = DateTimeUtil.splitDate(yesterdayDate,Constant.HYPHEN);

        //过滤出昨天的数据记录
        List<MedicationSituationEntity> list = medicationSituationService.list();
        Map<Integer, List<MedicationSituationEntity>> collect = list.stream().filter(item -> {
            Date sDate = item.getSDate();
            String ndate = DateTimeUtil.splitDate(sDate, Constant.HYPHEN);
            return ndate.equals(ydate);
        }).collect(Collectors.groupingBy(MedicationSituationEntity::getSUser));


        //生成消息记录
        collect.forEach((userId,mSList)->{
            MessageEntity messageEntity = new MessageEntity();

            messageEntity.setMsgSender(Constant.SENDER_APP);
            messageEntity.setMsgDateTime(date);
            messageEntity.setMsgRecipient(userId);
            messageEntity.setMsgIsRead(Constant.MSG_NOT_READ);


            Map<Integer, List<MedicationSituationEntity>> collect1 = mSList.stream().collect(Collectors.groupingBy(MedicationSituationEntity::getSMedicine));

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(DateTimeUtil.TransformDateTimeToString(yesterdayDate,Constant.HYPHEN)+" 用药情况\n");
            collect1.forEach((medicineId,mslist)->{
                Integer tatalDosing = 0;
                for(MedicationSituationEntity ms:mslist){
                    tatalDosing+=ms.getSDosing();
                }

                MedicineEntity medicine = medicineService.getById(medicineId);
                String mName = medicine.getMName();

                stringBuilder.append(mName + ": " + tatalDosing + medicine.getMUnit()+"\n");
            });

            messageEntity.setMsgContent(stringBuilder.toString());
            boolean save = messageService.baseSave(messageEntity);
            if(!save){
                logger.info("昨日用药定时任务出现错误");
            }
        });

        logger.info("昨日用药定时任务完成");


    }

    // 早上8点提醒用户吃药
    @Scheduled(cron = "0 0 8 * * *") // 每天早上8点

    // 每十秒执行一次
//    @Scheduled(cron = "*/10 * * * * *") // 每十秒
    public void remindMorningMedication() {
        logger.info("提醒用户早上吃药...");
        // 调用提醒用户吃药的方法，例如发送消息通知用户
        messageService.remindMedication(Constant.MORNING);
    }

    // 中午12点提醒用户吃药
    @Scheduled(cron = "0 0 12 * * *") // 每天中午12点

    // 每十秒执行一次
//    @Scheduled(cron = "*/10 * * * * *") // 每十秒
    public void remindNoonMedication() {
        logger.info("提醒用户中午吃药...");
        // 调用提醒用户吃药的方法，例如发送消息通知用户
        messageService.remindMedication(Constant.NOON);
    }

    // 晚上6点提醒用户吃药
    @Scheduled(cron = "0 0 18 * * *") // 每天晚上6点

    // 每十秒执行一次
//    @Scheduled(cron = "*/10 * * * * *") // 每十秒
    public void remindEveningMedication() {
        logger.info("提醒用户晚上吃药...");
        // 调用提醒用户吃药的方法，例如发送消息通知用户
        messageService.remindMedication(Constant.EVENING);
    }

    @Scheduled(cron = "0 30 18 * * *")

    public void sendEveningReminderEmails(){

//获取今天date
        Date date = new Date();
        String nowdate = DateTimeUtil.splitDate(date,Constant.HYPHEN);

        //过滤出今天的数据记录
        List<MedicationSituationEntity> list = medicationSituationService.list();
        Map<Integer, List<MedicationSituationEntity>> collect = list.stream()
                .collect(Collectors.groupingBy(MedicationSituationEntity::getSUser));

        //生成消息记录
        collect.forEach((userId,mSList)->{

            List<MedicationSituationEntity> collect1 = mSList.stream().filter(item -> {
                Date sDate = item.getSDate();
                String ndate = DateTimeUtil.splitDate(sDate, Constant.HYPHEN);
                return ndate.equals(nowdate);
            }).collect(Collectors.toList());

            int size = collect1.size();

            UserEntity byId = userService.getById(userId);
            UserDailyDosageEntity byId1 = userDailyDosageService.getById(userId);

            if(Objects.nonNull(byId1) && size==0){
                messageService.remindMedication(Constant.THREE_TIMES_UNTREATED);
            }
        });

        logger.info("晚上提醒用药定时任务完成");

    }


    @Scheduled(cron = "0 30 12 * * *")
    // 每十秒执行一次
//    @Scheduled(cron = "*/10 * * * * *") // 每十秒
    public void sendNoonReminderEmails(){

        //获取今天date
        Date date = new Date();
        String nowdate = DateTimeUtil.splitDate(date,Constant.HYPHEN);

        //过滤出今天的数据记录
        List<MedicationSituationEntity> list = medicationSituationService.list();
        Map<Integer, List<MedicationSituationEntity>> collect = list.stream()
                .collect(Collectors.groupingBy(MedicationSituationEntity::getSUser));

//        System.out.println(list);

        //生成消息记录
        collect.forEach((userId,mSList)->{
            List<MedicationSituationEntity> collect1 = mSList.stream().filter(item -> {
                Date sDate = item.getSDate();
                String ndate = DateTimeUtil.splitDate(sDate, Constant.HYPHEN);
                return ndate.equals(nowdate);
            }).collect(Collectors.toList());

            int size = collect1.size();

            UserEntity byId = userService.getById(userId);
            UserDailyDosageEntity byId1 = userDailyDosageService.getById(userId);
            if(Objects.nonNull(byId1) && size<=byId1.getDdFrequency()-1){
                messageService.remindMedication(Constant.TWICE_UNTREATED);
            }
        });

        logger.info("中午提醒用药定时任务完成");

    }

    @Scheduled(cron = "0 30 18 */2 * *")

//    @Scheduled(cron = "*/10 * * * * *") // 每十秒
    public void sendReminderEmails(){

        //获取今天date
        Date date = new Date();
        Date yesterdayDate = DateTimeUtil.getYesterdayDate();
        String nowdate = DateTimeUtil.splitDate(date,Constant.HYPHEN);

        //过滤出今天的数据记录
        List<MedicationSituationEntity> list = medicationSituationService.list();
        Map<Integer, List<MedicationSituationEntity>> collect = list.stream()
                .collect(Collectors.groupingBy(MedicationSituationEntity::getSUser));

//        System.out.println(list);

        //生成消息记录
        collect.forEach((userId,mSList)->{
            List<MedicationSituationEntity> collect1 = mSList.stream().filter(item -> {
                Date sDate = item.getSDate();
                String ndate = DateTimeUtil.splitDate(sDate, Constant.HYPHEN);
                return ndate.equals(nowdate) || ndate.equals(yesterdayDate);
            }).collect(Collectors.toList());

            int size = collect1.size();

            UserEntity byId = userService.getById(userId);
            UserDailyDosageEntity byId1 = userDailyDosageService.getById(userId);
            if(Objects.nonNull(byId1) && size==0 && Objects.nonNull(byId.getUEmail())){

                sendReminderEmailToUser(byId.getUEmail(),Constant.DEFAULT_DAYS_UNTREATED);

                EmergencyContactEntity byId2 = emergencyContactService.getById(byId.getUEmergencyContact());
                if(Objects.nonNull(byId2) && Objects.nonNull(byId2.getEcEmail())) {
                    sendReminderEmailToEC(byId2.getEcEmail(),byId.getUName()+"未按时吃药");
                }
            }
        });

        logger.info("提醒用药定时任务完成");

    }
    public Boolean sendReminderEmailToUser(String email,String meg) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
            mimeMessageHelper.setFrom(EmailUtil.mailObject);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("药箱系统");
            Context context = new Context();
            context.setVariable("meg",meg);
            String text = templateEngine.process("reminder-email-user.html", context);
            mimeMessageHelper.setText(text,true);
            javaMailSender.send(mimeMessage);
            logger.info("提醒用药定时任务完成");
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    public Boolean sendReminderEmailToEC(String email,String meg) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
            mimeMessageHelper.setFrom(EmailUtil.mailObject);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("药箱系统");
            Context context = new Context();
            context.setVariable("meg",meg);
            String text = templateEngine.process("reminder-email-ec.html", context);
            mimeMessageHelper.setText(text,true);
            javaMailSender.send(mimeMessage);
            logger.info("提醒用药定时任务完成");
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    @Scheduled(cron = "0 0 0 ? * MON") // 每周一的午夜执行
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
