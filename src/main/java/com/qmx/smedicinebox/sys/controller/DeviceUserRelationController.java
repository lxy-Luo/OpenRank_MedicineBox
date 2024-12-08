package com.qmx.smedicinebox.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qmx.smedicinebox.sys.entity.DeviceUserRelationEntity;
import com.qmx.smedicinebox.sys.service.DeviceUserRelationService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;



/**
 * 设备与用户的关联
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@RestController
@RequestMapping("sys/deviceuserrelation")
public class DeviceUserRelationController {
    @Autowired
    private DeviceUserRelationService deviceUserRelationService;

    /**
     * 列表
     * @param params
     * @return
     */
    @PostMapping("/listpage")
    public R listpage(@RequestParam Map<String, Object> params){
        PageUtils page = deviceUserRelationService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     * @param durId
     * @return
     */
    @GetMapping("/info/{durId}")
    public R info(@PathVariable("durId") Integer durId){
		DeviceUserRelationEntity deviceUserRelation = deviceUserRelationService.getById(durId);

        return R.ok().put("deviceUserRelation", deviceUserRelation);
    }

    /**
     * 保存
     * @param deviceUserRelation
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody DeviceUserRelationEntity deviceUserRelation){
		deviceUserRelationService.save(deviceUserRelation);

        return R.ok();
    }

    /**
     * 修改
     * @param deviceUserRelation
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody DeviceUserRelationEntity deviceUserRelation){
		deviceUserRelationService.updateById(deviceUserRelation);
        return R.ok();
    }

    /**
     * 删除
     * @param durIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] durIds){
		deviceUserRelationService.removeByIds(Arrays.asList(durIds));
        return R.ok();
    }

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
