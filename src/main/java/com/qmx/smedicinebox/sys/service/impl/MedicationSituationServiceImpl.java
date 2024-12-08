package com.qmx.smedicinebox.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.constant.Sensor;
import com.qmx.smedicinebox.sys.dao.MedicationSituationDao;
import com.qmx.smedicinebox.sys.dao.MedicineDao;
import com.qmx.smedicinebox.sys.dao.UserDao;
import com.qmx.smedicinebox.sys.entity.MedicationSituationEntity;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.sys.service.MedicationSituationService;
import com.qmx.smedicinebox.sys.service.MedicineService;
import com.qmx.smedicinebox.sys.service.UserService;
import com.qmx.smedicinebox.utils.*;
import com.qmx.smedicinebox.vo.MedicationSituationModifyVo;
import com.qmx.smedicinebox.vo.MedicationSituationVo;
import com.qmx.smedicinebox.vo.MedicineHistory;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.Session;
import org.apache.tsfile.enums.TSDataType;
import org.apache.tsfile.read.common.RowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("medicationSituationService")
public class MedicationSituationServiceImpl extends ServiceImpl<MedicationSituationDao, MedicationSituationEntity> implements MedicationSituationService {
    @Autowired
    private UserService userService;

    @Autowired
    private DeviceUserRelationServiceImpl deviceUserRelationService;
    @Autowired
    private MedicineDao medicineDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private MedicationSituationDao medicationSituationDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MedicationSituationEntity> page = this.page(
                new Query<MedicationSituationEntity>().getPage(params),
                new QueryWrapper<MedicationSituationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R medicationSituationList(Integer userId, String date) {
        QueryWrapper<UserEntity> userWapper = new QueryWrapper<>();
        userWapper.eq("u_id", userId);
        UserEntity one = userService.getOne(userWapper);
        if(Objects.isNull(one)){
            return R.error("未找到相应用户");
        }

        QueryWrapper<MedicationSituationEntity> msWrapper = new QueryWrapper<>();
        msWrapper.eq("s_user",userId);
        List<MedicationSituationEntity> medicationSituationEntities = this.baseMapper.selectList(msWrapper);

        List<MedicationSituationVo> collect = medicationSituationEntities.stream().filter(item -> {
            //匹配相应日期
            String s = DateTimeUtil.splitDate(item.getSDate(),Constant.HYPHEN);
            log.info("date"+s);
            return s.equals(date);
        }).map(item -> {

            MedicationSituationVo medicationSituationVo = new MedicationSituationVo();
            medicationSituationVo.setId(item.getSId());

            String datetime = DateTimeUtil.splitDate(item.getSDate(),Constant.SLASH);
            medicationSituationVo.setDate(DateTimeUtil.removeLeadingZeros(datetime, Constant.SLASH));
            medicationSituationVo.setUnit(item.getSUnit());
            medicationSituationVo.setTime(DateTimeUtil.splitTimeNotSecond(item.getSDate(),Constant.COLON));
            medicationSituationVo.setDrugId(item.getSMedicine());
            medicationSituationVo.setAll_number(item.getSDosing());
            medicationSituationVo.setUserId(userId);
            medicationSituationVo.setImage(item.getSPic());
            return medicationSituationVo;

        }).collect(Collectors.toList());
        log.info("成功");
        System.out.println("成功");
        System.out.println(collect);
        return R.ok().put("date",collect);
    }

    @Override
    public R save(MedicationSituationVo mSVo, HttpSession session) {
        MedicationSituationEntity mSEntity = new MedicationSituationEntity();

        mSEntity.setSPic(mSVo.getImage());
        mSEntity.setSDosing(mSVo.getAll_number());
        mSEntity.setSUnit(mSVo.getUnit());
        mSEntity.setSMedicine(mSVo.getDrugId());
        mSEntity.setSUser(mSVo.getUserId());

        String date = DateTimeUtil.addLeadingZeros(mSVo.getDate(),Constant.HYPHEN);
        String time = mSVo.getTime();
        String datetime = date + " " + time;
        Date dateTime = DateTimeUtil.TransformStringToDateTimeNotSecond(datetime,Constant.HYPHEN,Constant.COLON);
        mSEntity.setSDate(dateTime);
        mSEntity.setSTime(dateTime);

        int insert = this.baseMapper.insertMs(mSEntity);
        if(insert == 0){
            return R.error("保存失败");
        }

        return R.ok();
    }
    @Override
    public R save(MedicationSituationVo mSVo) {
        MedicationSituationEntity mSEntity = new MedicationSituationEntity();

        mSEntity.setSPic(mSVo.getImage());
        mSEntity.setSDosing(mSVo.getAll_number());
        mSEntity.setSUnit(mSVo.getUnit());
        mSEntity.setSMedicine(mSVo.getDrugId());
        mSEntity.setSUser(mSVo.getUserId());
        String date1 = mSVo.getDate();
        String time1 = mSVo.getTime();
        if(Objects.isNull(date1) || date1 == ""){
            date1 = "1970-01-01";
        }
        if(Objects.isNull(time1) || time1 == ""){
            time1 = "00:00";
        }

        String date = DateTimeUtil.addLeadingZeros(date1,Constant.HYPHEN);

        String datetime = date + " " + time1;
        Date dateTime = DateTimeUtil.TransformStringToDateTimeNotSecond(datetime,Constant.HYPHEN,Constant.COLON);
        mSEntity.setSDate(dateTime);
        mSEntity.setSTime(dateTime);

        int insert = this.baseMapper.insertMs(mSEntity);
        if(insert == 0){
            return R.error("保存失败");
        }

        return R.ok();
    }

    @Override
    public void insertNewRecord(Integer deviceId, String link, MedicineEntity medicine, String userIdentityId) {
        MedicationSituationEntity medicationSituationEntity = new MedicationSituationEntity();
        Date date = new Date();
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<UserEntity>().eq("u_identity_id", userIdentityId));
        if(Objects.isNull(userEntity)){
            return;
        }
        Integer userId = userEntity.getUId();
        medicationSituationEntity.setSDate(date);
        medicationSituationEntity.setSTime(date);
        medicationSituationEntity.setSPic(link);
        medicationSituationEntity.setSDosing(4);
        medicationSituationEntity.setSMedicine(medicine.getMId());
        medicationSituationEntity.setSUnit(medicine.getMUnit());
        medicationSituationEntity.setSUser(userId);

        int insert = this.baseMapper.baseInsert(medicationSituationEntity);
    }

    @Override
    public boolean determineSave(Integer medicineId) {
        QueryWrapper<MedicationSituationEntity> wrapper = new QueryWrapper<>();

        // 获取当前时间
        Date now = new Date();

        // 计算五分钟前的时间
        Date fiveMinutesAgo = new Date(now.getTime() - 5 * 60 * 1000);
        System.out.println(fiveMinutesAgo);

        wrapper.ge("s_date", fiveMinutesAgo).le("s_date", now);
        // 执行查询
        List<MedicationSituationEntity> medicationSituationEntities = this.baseMapper.selectList(wrapper);

        System.out.println(medicationSituationEntities);

        for(int i=0;i<medicationSituationEntities.size();i++){
            MedicationSituationEntity medicationSituationEntity = medicationSituationEntities.get(i);
            Integer sMedicine = medicationSituationEntity.getSMedicine();
            if(sMedicine.equals(medicineId)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void updateById(MedicationSituationModifyVo medicationSituation) {
        String date = medicationSituation.getDate();
        String time = medicationSituation.getTime();
        if(Objects.isNull(date) || time == ""){
            date = "1970-01-01";
        }
        if(Objects.isNull(time) || time == ""){
            time = "00:00";
        }
        String datetime = date + " " + time;
        Date dateTime = DateTimeUtil.TransformStringToDateTimeNotSecond(datetime,Constant.HYPHEN,Constant.COLON);
        MedicationSituationEntity medicationSituationEntity = new MedicationSituationEntity();
        medicationSituationEntity.setSDate(dateTime);
        medicationSituationEntity.setSUser(medicationSituation.getUserId());
        medicationSituationEntity.setSTime(dateTime);
        medicationSituationEntity.setSDosing(medicationSituation.getAll_number());
        medicationSituationEntity.setSUnit(medicationSituation.getUnit());
        medicationSituationEntity.setSPic(medicationSituation.getImage());
        medicationSituationEntity.setSMedicine(medicationSituation.getDrugId());
        this.baseMapper.updateById(medicationSituationEntity);
    }

    @Override
    public R getHistory(Integer userId,Integer page) {
        page = (page-1)*7;
        List<MedicineHistory> res = medicationSituationDao.getHistoryByUserId(userId,page);
        return R.ok(Map.of("list", res));
    }

    //根据日期查询IoTDB
    public R getDataByDate(Session session, Integer userId, String date) {
        try {
            //时间序列
            List<String> paths=new ArrayList<>();
            paths.add(Sensor.frontDevicePath+userId+"."+Sensor.sensor1);
            paths.add(Sensor.frontDevicePath+userId+"."+Sensor.sensor2);
            //起始时间
            //将具体时间转换为时间戳
            //因为时间戳不支持精确到日期，所以在后面加上秒
            date=date+" 00:00:00";
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime=LocalDateTime.parse(date,formatter);
            //将时间戳转化为毫秒值
            long startTime = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            //结束时间(加上一天的毫秒值)
            long endTime=startTime+86400000;
            SessionDataSet sessionDataSet = session.executeRawDataQuery(paths, startTime, endTime);
            //暂时只打印到控制台
            while(sessionDataSet.hasNext()) {
                RowRecord next = sessionDataSet.next();
                System.out.println(next);
            }
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
        return R.ok("IoT根据日期查询成功");
    }

    /**
     * IoT数据新增数据
     * @param session
     * @param userId
     * @param values
     * @return
     */
    @Override
    public R insertRecord(Session session, Integer userId, List<Object> values) {
        //判断时间序列是否存在
        String timeSeries= Sensor.frontDevicePath+userId+"."+Sensor.sensor1;
        try {
            if(session.checkTimeseriesExists(timeSeries)){
                //设备路径
                String deviceId=Sensor.frontDevicePath+userId;
                //时间毫秒值
                long currentTimeMillis = System.currentTimeMillis();
                //传感器List
                List<String> measurements=new ArrayList<>();
                measurements.add(Sensor.sensor1);
                measurements.add(Sensor.sensor2);
                //数据类型
                List<TSDataType> dataTypes=new ArrayList<>();
                dataTypes.add(TSDataType.INT32);
                dataTypes.add(TSDataType.INT32);
                //插入数据
                session.insertRecord(deviceId,currentTimeMillis,measurements,dataTypes,values);


            }else {
                return R.error("未找到该时间序列");
            }
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
        return R.ok("插入数据成功");
    }


    /**
     * IoTDB批量插入数据
     * @param session
     * @param uIds
     * @param valueList
     * @return
     */
    public R insertRecords(Session session, List<Integer> uIds, List<List<Object>> valueList) {
        String timeSeries= Sensor.frontDevicePath+uIds.get(0)+"."+Sensor.sensor1;
        try {
            if(session.checkTimeseriesExists(timeSeries)){
                //生成设备路径deviceIds和时间毫秒值timeList
                List<String> deviceIds=new ArrayList<>();
                List<Long> timeList=new ArrayList<>();
                List<List<TSDataType>> dataTypes=new ArrayList<>();
                List<List<String>> meas=new ArrayList<>();
                for (Integer uId : uIds) {
                    String path=Sensor.frontDevicePath+uId;
                    deviceIds.add(path);
                    timeList.add(System.currentTimeMillis());
                    List<TSDataType> dataType=new ArrayList<>();
                    dataType.add(TSDataType.INT32);
                    dataType.add(TSDataType.INT32);
                    dataTypes.add(dataType);
                    //传感器List
                    List<String> measurements=new ArrayList<>();
                    measurements.add(Sensor.sensor1);
                    measurements.add(Sensor.sensor2);
                    meas.add(measurements);
                }
                session.insertRecords(deviceIds,timeList,meas,dataTypes,valueList);
            }else {
                return R.error("未找到该时间序列");
            }

        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
        return R.ok("批量插入数据成功");
    }

}