package com.qmx.smedicinebox.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.utils.ocr.AliyunOcrUtil;
import com.qmx.smedicinebox.utils.ocr.BaiduOcrUtil;
import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.sys.dao.MedicationPictureDao;
import com.qmx.smedicinebox.sys.dao.UserDao;
import com.qmx.smedicinebox.sys.entity.MedicationPictureEntity;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.service.DailyMedicationPlanService;
import com.qmx.smedicinebox.sys.service.MedicationPictureService;
import com.qmx.smedicinebox.sys.service.MedicationSituationService;
import com.qmx.smedicinebox.sys.service.MedicineService;
import com.qmx.smedicinebox.utils.*;
import com.qmx.smedicinebox.utils.ocr.OcrUtil;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service("medicationPictureService")
public class MedicationPictureServiceImpl extends ServiceImpl<MedicationPictureDao, MedicationPictureEntity> implements MedicationPictureService {
    @Autowired
    private MedicationSituationService medicationSituationService;

    @Autowired
    private DailyMedicationPlanService dailyMedicationPlanService;

    @Autowired
    private MedicineService medicineService;
    @Autowired
    private UserDao userDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MedicationPictureEntity> page = this.page(
                new Query<MedicationPictureEntity>().getPage(params),
                new QueryWrapper<MedicationPictureEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public R getImgBin(String josn) {

        String folderTest = "src/main/resources/static";
//        String folderTest = "/www/wwwroot/tmp";

        JSONObject jsonObject = new JSONObject(josn);
        String deviceId = jsonObject.optString("deviceId");
        String userIdentityId = jsonObject.optString("userIdentityId");
        String hexString = jsonObject.optString ("hexString");

        if (hexString == null || hexString.isEmpty() ||
                deviceId == null || deviceId.isEmpty() ||
                userIdentityId == null || userIdentityId.isEmpty()) {
            return R.error("参数错误");
        }

        Date date = new Date();
        log.info("图片传递完成");

        // 标识前置信息
        MedicationPictureEntity medicationPictureEntity = new MedicationPictureEntity();
        medicationPictureEntity.setPDate(date);
        medicationPictureEntity.setPDevice(Integer.parseInt(deviceId));

        ImageIO.setCacheDirectory(new File(folderTest));

        // 将十六进制的字符串转换为字节数组
        byte[] imageBytes = DatatypeConverter.parseHexBinary(hexString);
        // 将字节数组转换为输入流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        // 读取输入流中的数据，生成图片并保存到指定路径
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 默认选择在项目根路径下
        File folder = new File(folderTest);  // 将图片打开
        if(!folder.exists()){
            folder.mkdir();
        }


        // 获取当前时间，转成字符串
        String format = DateTimeUtil.TransformDateTimeToString(date, Constant.EMPTY,Constant.UNDERSCORE,Constant.EMPTY);
//        String path = img.getUserId() + "_" + id + "_" + "temImg.png";
        String path = deviceId + "_" + format + ".png";
//        String path = "26_2023-5-25.png";
//        File file = new File(folder, path);
//        String filePath = "/www/wwwroot/tmp" + "/" + path;
        String filePath = folderTest + "/" + path;
        log.info("图片保存成功");
        File file = new File(filePath);

//        file.createNewFile();
//        ImageIO.setUseCache();  //设置内存缓存
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        OcrUtil ocrUtil = new AliyunOcrUtil();

        //调用aliyun ocr进行图片的文字识别
        log.info("图片识别开始");
        String s = ocrUtil.generalBasic(filePath);
        List<String> wordList = ocrUtil.generalWordList(s);

        log.info("图片识别完成");
        log.info("开始匹配药品");
        //对文字进行匹配
        MedicineEntity medicine = medicineService.equalMedicineName(wordList);

        //匹配度不合适则意味着图片不清晰，返回重拍
        if(Objects.isNull(medicine)){
            log.info("图片识别不了,请重新拍摄");
            return R.error("图片识别不了");
        }

        //判断将要保存的用药记录在之前是不是在最近已经服用过
//        boolean flag = medicationSituationService.determineSave(medicine.getMId());
//        if(flag == false){
//            System.out.println("这个药品近五分钟已经吃过一遍了");
//            return R.error("这个药品近五分钟已经吃过一遍了");
//        }
        //更新查询今日用药计划
        R r = dailyMedicationPlanService.updateById(userIdentityId, medicine.getMId());
        //判断这个药是不是自己的计划
        if (r.getCode() != 0) {
            return r;
        }

        // 调用工具类将图片上传
        String link = OssUtil.upload(file,path);
        System.out.println(link);

        // 将链接保存到数据库
        medicationPictureEntity.setPFilepath(link);
//        System.out.println("需要保存的IMG数据： " + link);


        //新增一条新的用药情况记录
        System.out.println("新增一条新的用药情况记录");
        medicationSituationService.insertNewRecord(Integer.valueOf(deviceId),link,medicine,userIdentityId);

        //新增一张图片
        int insert = this.baseMapper.baseInsert(medicationPictureEntity);
        if(insert == 0){
            return R.error("插入数据失败");
        }

        // 将临时文件删除
        if (!file.delete()) {
            System.out.println("Failed to delete file: " + file.getAbsolutePath());
        }

        //查询今日用药计划
//        R result = dailyMedicationPlanService.returnDailyMedicationPlan(deviceId);
        R result = R.ok();
        return result;
    }

}