package com.qmx.smedicinebox.sys.pccontroller;

import com.qmx.smedicinebox.sys.entity.DeviceUserRelationEntity;
import com.qmx.smedicinebox.sys.service.DeviceUserRelationService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 设备与用户的关联
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@RestController("DeviceUserRelationControllerPC")
@RequestMapping("pc/deviceuserrelation")
public class DeviceUserRelationController {
    @Autowired
    private DeviceUserRelationService deviceUserRelationService;


    /**
     * 获取所有数据
     * @return
     */
    @GetMapping("/list")
    public R list(){
        List<DeviceUserRelationEntity> list = deviceUserRelationService.list();
        return R.ok().put("list", list);
    }

    /**
     * 获取用户当天所关联设备的图片
     * @param userId
     * @param date
     * @return
     */
    @GetMapping("/getpictures/{userId}/{date}")
    @Operation(summary = "获取用户当天所关联设备的图片",description = "传入用户id和时间（yyyy-MM-dd)")
    public R getPictures(@PathVariable("userId") Integer userId,@PathVariable("date") String date){
        R pictures = deviceUserRelationService.getPictures(userId,date);
        return pictures;
    }
}
