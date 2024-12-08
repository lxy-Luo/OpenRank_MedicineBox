package com.qmx.smedicinebox.sys.hardwarecontroller;

import com.qmx.smedicinebox.sys.entity.MedicationPictureEntity;
import com.qmx.smedicinebox.sys.service.MedicationPictureService;
import com.qmx.smedicinebox.sys.service.MedicationSituationService;
import com.qmx.smedicinebox.sys.service.UserService;
import com.qmx.smedicinebox.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 硬件控制器
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
@Slf4j
@RestController("HardWareController")
@RequestMapping("hardware/")
public class HardWareController {
    @Autowired
    private MedicationPictureService medicationPictureService;

    @Autowired
    private UserService userService;

    /**
     * 上传图片（硬件接口）
     * @param json
     * @return
     */
    @PostMapping("medicationpicture/bin")
//    @Operation(summary = "上传图片",description = "这个是硬件的接口")
    public R getImgBin(@RequestBody String json){
        R result = medicationPictureService.getImgBin(json);
        return result;
    }

    /**
     * 扫脸绑定账户(硬件接口)
     * @param json
     * @return
     */
    @PostMapping("user/bindAccountWithFaceScan")
    public R bindAccountWithFaceScan(@RequestBody String json){
        R result = userService.bindAccountWithFaceScan(json);
        return result;
    }

    /**
     * 扫脸登录Post接口(硬件接口)
     * @param json
     * @return
     */
    @PostMapping("user/logInFace")
    public R logInFace(@RequestBody String json) {
        R result = userService.logIn(json);
        return result;
    }

}
