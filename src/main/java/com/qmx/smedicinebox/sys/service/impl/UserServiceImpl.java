package com.qmx.smedicinebox.sys.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.constant.Sensor;
import com.qmx.smedicinebox.dto.UserLoginDto;
import com.qmx.smedicinebox.dto.UserRegisterDto;
import com.qmx.smedicinebox.dto.UserVerificationDto;
import com.qmx.smedicinebox.sys.dao.DeviceDao;
import com.qmx.smedicinebox.sys.dao.DeviceUserRelationDao;
import com.qmx.smedicinebox.sys.dao.UserDao;
import com.qmx.smedicinebox.sys.entity.DeviceUserRelationEntity;
import com.qmx.smedicinebox.sys.entity.EmergencyContactEntity;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.sys.service.*;
import com.qmx.smedicinebox.utils.*;
import com.qmx.smedicinebox.vo.UserEntityInfoVO;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.Session;
import org.apache.tsfile.enums.TSDataType;
import org.apache.tsfile.file.metadata.enums.CompressionType;
import org.apache.tsfile.file.metadata.enums.TSEncoding;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.InputStream;
import java.util.*;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;

    @Autowired
    private UserDailyDosageService userDailyDosageService;

    @Autowired
    private EmergencyContactService emergencyContactService;

    @Autowired
    private DeviceUserRelationDao deviceUserRelationDao;

    @Autowired
    private DailyMedicationPlanService dailyMedicationPlanService;
    @Autowired
    private DeviceDao deviceDao ;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R logIn(UserLoginDto user) {
        //根据用户名和密码查询
        QueryWrapper<UserEntity> wrapper = Wrappers.query();
        String uUsername = user.getUUsername();
        wrapper.eq("u_username", uUsername);
        UserEntity userEntity = this.baseMapper.selectOne(wrapper);

        if(Objects.isNull(userEntity)){
            return R.error("未查询到相应用户");
        }

        if(userEntity.getUIsValid().equals(UserEntity.INVALID)){
            return R.error("该账户未激活");
        }

        if(!userEntity.getUPassword().equals(user.getUPassword())){
            return R.error("密码错误");
        }

        return R.ok().put("userId",userEntity.getUId())
                .put("msg","登陆成功")
                .put("name",userEntity.getUName())
                .put("email",userEntity.getUEmail())
                .put("phone",userEntity.getUPhone())
                .put("avatar",userEntity.getUAvatar())
                .put("ecId",userEntity.getUEmergencyContact());

    }

    @Override
    public R bindAccountWithFaceScan(String json) {
        //参数转换
        JSONObject jsonObject = new JSONObject(json);

        String deviceId = jsonObject.optString("deviceId");
        String userIdentityId = jsonObject.optString("userIdentityId");

        if (deviceId == null || deviceId.isEmpty() ||
                userIdentityId == null || userIdentityId.isEmpty()) {
            return R.error("参数错误");
        }

        UserEntity userEntity1 = this.baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("u_identity_id", userIdentityId));
        if(Objects.nonNull(userEntity1)){
            return R.error("该用户已经注册过了");
        }

        //生成用户
        UserEntity userEntity = new UserEntity();
        userEntity.setUIdentityId(userIdentityId);
        userEntity.setUUsername(userIdentityId);
        userEntity.setUName(userIdentityId);
        userEntity.setUPassword(UserEntity.DEFAULT_PASSWORD);
        userEntity.setUIsValid(UserEntity.VALID);
        this.baseMapper.baseInsert(userEntity);

        //绑定设备
        DeviceUserRelationEntity deviceUserRelationEntity = new DeviceUserRelationEntity();
        deviceUserRelationEntity.setDurUser(userEntity.getUId());
        deviceUserRelationEntity.setDurDevice(Integer.valueOf(deviceId));
        int i = deviceUserRelationDao.insertDeviceUserRelation(deviceUserRelationEntity);
        if (i > 0) {
            return R.ok("绑定成功");
        }
        return R.error("绑定失败");
    }

    @Override
    public R logIn(String json) {
        JSONObject jsonObject = new JSONObject(json);

        String userIdentityId = jsonObject.optString("userIdentityId");
        System.out.println("userIdentityId: " + userIdentityId);

        if (userIdentityId == null || userIdentityId.isEmpty()) {
            System.out.println("参数错误");
            return R.error("参数错误");
        }

        UserEntity userEntity = this.baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("u_identity_id", userIdentityId));
        if(Objects.isNull(userEntity)){
            return R.error("未查询到相应用户");
        }

        //查询今日用药计划
//        R result = dailyMedicationPlanService.returnDailyMedicationPlan(userIdentityId);
        R result = R.ok().put("name",userEntity.getUName());
        return result;
    }


    @Override
    public List<UserEntity> selectUserEntityListByDeviceId(Integer deviceId) {
        List<DeviceUserRelationEntity> durDevice = deviceUserRelationDao.selectList(new QueryWrapper<DeviceUserRelationEntity>().eq("dur_device", deviceId));
        ArrayList<UserEntity> userEntitiList = new ArrayList<>();
        for(DeviceUserRelationEntity dur : durDevice) {
            Integer userId = dur.getDurUser();
            userEntitiList.add(this.baseMapper.selectById(userId));
        }
        return userEntitiList;
    }
    /*
     *根据UserEntityID获取用户完整info
     * */
    @Override
    public UserEntityInfoVO selectUserInfoByUserId(Integer userId) {
        UserEntityInfoVO userEntityInfoVO = new UserEntityInfoVO();
        //获取用户基本信息
        UserEntity userEntity = this.baseMapper.selectById(userId);
        BeanUtils.copyProperties(userEntity, userEntityInfoVO);

        //加入紧急联系人信息
        Integer EmergencyContactID = userEntity.getUEmergencyContact();
        if(EmergencyContactID != null){
            EmergencyContactEntity emergencyContactEntity = emergencyContactService.selectById(EmergencyContactID);
            userEntityInfoVO.setEmergencyContactName(emergencyContactEntity.getEcName());
            userEntityInfoVO.setEmergencyContactPhone(emergencyContactEntity.getEcPhone());
        }
        return userEntityInfoVO;
    }




    @Override
    public R register(UserRegisterDto user) {
//        检验该账户是否注册
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEntity::getUEmail, user.getUEmail());
        UserEntity registerUser = this.baseMapper.selectOne(lambdaQueryWrapper);

        if(Objects.nonNull(registerUser)&&registerUser.getUIsValid().equals(UserEntity.VALID)){
//账户不为空且已激活,说明账户已被注册过
            return R.error("此账户已被注册");
        }else{
//            账户为空或者未激活,都可以将原来的账户内容删除,进行新的创建
            this.baseMapper.delete(lambdaQueryWrapper);
        }

//        验证邮箱格式
        Boolean qqEmail = EmailUtil.isQQEmail(user.getUEmail());
        Boolean neteaseEmail = EmailUtil.isNeteaseEmail(user.getUEmail());
        if(!qqEmail&&!neteaseEmail){
            return R.error("账号格式有误");
        }


//        为用户创建账号
        UserEntity users = new UserEntity();
        users.setUUsername(user.getUUsername());
        users.setUEmail(user.getUEmail());
        users.setUName(user.getUName());
        users.setUPassword(user.getUPassword());
        Boolean account = createAccount(users);

        if(!account){
            return R.error("创建账号出错");
        }

//        向用户发送验证码
        Boolean aBoolean = sendRegisterEmail(user.getUEmail(),users.getUVerifyCode());
        if(!aBoolean){
            return R.error("发送邮件失败");
        }else{
            return R.ok("发送邮件成功，请及时查收");
        }
    }

    @Override
    public R verification(UserVerificationDto user) {
        System.out.println(user);
        Date date = new Date();
        //查询该账号
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEntity::getUEmail, user.getUEmail());
        UserEntity registerUser = this.baseMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(registerUser)) {
            return R.error("未找到该注册账号");
        }

        if (registerUser.getUIsValid().equals(UserEntity.INVALID)) {
//            当账户存在但未激活时，进行后续的逻辑
            if (Objects.isNull(user.getUVerifyCode())) {
//                如果验证码为空，传入参数有误，不能激活账号
                return R.error("该账户未激活或验证码有误");

            } else if (user.getUVerifyCode().equals(registerUser.getUVerifyCode())
                    && DateTimeUtil.PreviousToNow(registerUser.getUVerifyCodeTime(), date)<UserEntity.VERIFYCODE_TIME_LIMIT) {
//                验证码正确且验证码未超时，激活账号并更新账号
                registerUser.setUIsValid(UserEntity.VALID);

                System.out.println(registerUser);


                userDailyDosageService.saveDefault(registerUser.getUId());

                EmergencyContactEntity emergencyContactEntity = emergencyContactService.saveDefault(registerUser.getUId());
                System.out.println("emergencyContactEntity");
                System.out.println(emergencyContactEntity);
                registerUser.setUEmergencyContact(emergencyContactEntity.getEcId());
                this.baseMapper.updateById(registerUser);

                return R.ok("账户激活成功");

            } else if(user.getUVerifyCode().equals(registerUser.getUVerifyCode())
                    && DateTimeUtil.PreviousToNow(registerUser.getUVerifyCodeTime(),date)>UserEntity.VERIFYCODE_TIME_LIMIT){
//                验证码正确，但是超时了，返回该逻辑
                return R.error("验证码时间已过期,请重新发送邮件");

            }else {
//验证码出错
                return R.error("验证码错误");
            }
        }else if(registerUser.getUIsValid().equals(UserEntity.VALID)){
//            如果账户的isValid为真，则说明这个账户已经存在并且激活
            return R.error("已存在该用户");
        }else{
//            如果账户的isValid为null，则说明数据有问题
            return R.error("IsValid is mast "+UserEntity.VALID+" or "+UserEntity.INVALID);
        }
    }

    @Override
    public R getAvatar(Integer userId) {
        UserEntity userEntity = this.baseMapper.selectById(userId);
        if(userEntity == null) {
            return R.error(404,"未找到用户");
        }
        String url = userEntity.getUAvatar();
        return R.ok().put("url",url);
    }

    @Override
    public R updateAvatar(Integer userId, MultipartFile avatarFile) {
        UserEntity userEntity = this.baseMapper.selectById(userId);
        if(userEntity == null) {
            return R.error(404,"未找到用户");
        }
        if (avatarFile.isEmpty()) {
            return R.error(400, "文件为空");
        }

        try {
            String link = null;
            try(InputStream avatarInputStream = avatarFile.getInputStream()){
                link = OssUtil.uploadAvatar(avatarInputStream,avatarFile.getOriginalFilename(),userId);
            }
            if(link == null){
                return R.error(400,"文件上传失败");
            }

            // 更新用户头像URL
            String uAvatar = userEntity.getUAvatar();
            userEntity.setUAvatar(link);

            this.baseMapper.updateById(userEntity);

            return R.ok("头像更新成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    注册账号时创建账号
    public Boolean createAccount(UserEntity user){
//        赋值为未激活
        user.setUIsValid(UserEntity.INVALID);


//生成验证码
        String s = IdUtil.getSnowflake(1, 1).nextIdStr();
        user.setUVerifyCode(s.substring(s.length() - 4));


////        加密密码
//        if(!"".equals(user.getUPassword())&&!Objects.isNull(user.getUPassword())){
//            String s2 = passwordEncoder.encode(user.getPassword());
//            user.setPassword(s2);
//        }

//生成或更新验证密码
        System.out.println(new Date());
        user.setUVerifyCodeTime(new Date());

        int insert = this.baseMapper.baseInsert(user);
        return insert==0?false:true;
    }

    //    发送邮件
    public Boolean sendRegisterEmail(String email,String verifyCode) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
            mimeMessageHelper.setFrom(EmailUtil.mailObject);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("药箱系统欢迎你");
            Context context = new Context();
            System.out.println(verifyCode);
            context.setVariable("verifyCode",verifyCode);
            String text = templateEngine.process("active-account.html", context);
            mimeMessageHelper.setText(text,true);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    @Override
    public R createTimeSeries(Session session, Integer uId) {
        //设备路径
        String devicePath= Sensor.frontDevicePath+uId;
        //传感器路径
        List<String> paths=new ArrayList<>();
        paths.add(devicePath+"."+Sensor.sensor1);
        paths.add(devicePath+"."+Sensor.sensor2);
        //数据类型
        List<TSDataType> dataTypes=new ArrayList<>();
        dataTypes.add(TSDataType.INT32);
        dataTypes.add(TSDataType.INT32);
        //编码
        List<TSEncoding> encodings=new ArrayList<>();
        encodings.add(TSEncoding.PLAIN);
        encodings.add(TSEncoding.PLAIN);
        //压缩方式
        List<CompressionType> compressionTypes=new ArrayList<>();
        compressionTypes.add(CompressionType.UNCOMPRESSED);
        compressionTypes.add(CompressionType.UNCOMPRESSED);

        try {
            //检查时间序列是否存在
            if (session.checkTimeseriesExists(paths.get(0))){
                //时间序列存在
                return R.error("用户已存在，请勿重复注册");
            }else {
                //创建时间序列
                session.createMultiTimeseries(paths,dataTypes,encodings,compressionTypes,
                        null,null,null,null);
            }
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
        return R.ok("创建时间序列成功");
    }

    public R deleteTimeSeries(Session session, Integer[] uIds) {
        List<String> devicePaths=new ArrayList<>();
        //生成设备路径
        for (Integer uId : uIds) {
            devicePaths.add(Sensor.frontDevicePath+uId);
        }
        //生成时间序列路径
        List<String> paths=new ArrayList<>();
        for (String devicePath : devicePaths) {
            paths.add(devicePath+"."+Sensor.sensor1);
            paths.add(devicePath+"."+Sensor.sensor2);
        }
        //检测时间序列是否存在，不存在则报错
        try {
            if(session.checkTimeseriesExists(paths.get(0))){
                //根据时间序列路径删除时间序列
                session.deleteTimeseries(paths);
            }else {
                return R.error("时间序列不存在，删除错误");
            }
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }

        return R.ok("删除成功");
    }

}