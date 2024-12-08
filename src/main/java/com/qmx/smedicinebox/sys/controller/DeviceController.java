package com.qmx.smedicinebox.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qmx.smedicinebox.sys.entity.DeviceEntity;
import com.qmx.smedicinebox.sys.service.DeviceService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;



/**
 * 设备
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@RestController
@RequestMapping("sys/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

//    /**
//     * 列表
//     * @param params
//     * @return
//     */
//    @PostMapping("/listpage")
//    public R listpage(@RequestParam Map<String, Object> params){
//        PageUtils page = deviceService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


    /**
     * 信息
     * @param dId
     * @return
     */
    @GetMapping("/info/{dId}")
    public R info(@PathVariable("dId") Integer dId){
		DeviceEntity device = deviceService.getById(dId);
        return R.ok().put("device", device);
    }

    /**
     * 查询所有设备
     * @return
     */
    @GetMapping("/list")
    public R list(){
        List<DeviceEntity> list = deviceService.list();
        return R.ok().put("list", list);
    }

    /**
     * 保存
     * @param device
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody DeviceEntity device){
		deviceService.save(device);

        return R.ok();
    }

    /**
     * 修改
     * @param device
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody DeviceEntity device){
		deviceService.updateById(device);

        return R.ok();
    }

    /**
     * 删除
     * @param dIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] dIds){
		deviceService.removeByIds(Arrays.asList(dIds));
        return R.ok();
    }

}
