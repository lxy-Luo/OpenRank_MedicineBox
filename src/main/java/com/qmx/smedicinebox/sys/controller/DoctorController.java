package com.qmx.smedicinebox.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qmx.smedicinebox.sys.entity.DoctorEntity;
import com.qmx.smedicinebox.sys.service.DoctorService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;



/**
 *  医生信息表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 06:34:29
 */
@RestController
@RequestMapping("sys/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

//    /**
//     * 列表
//     */
//    @PostMapping("/listPage")
//    public R listPage(@RequestParam Map<String, Object> params){
//        PageUtils page = doctorService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }

    /**
     * 列表
     * @return
     */
    @GetMapping("/list")
    public R list(){
        List<DoctorEntity> list = doctorService.list();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     * @param dId
     * @return
     */
    @GetMapping("/info/{dId}")
    public R info(@PathVariable("dId") Integer dId){
		DoctorEntity doctor = doctorService.getById(dId);
        return R.ok().put("doctor", doctor);
    }

    /**
     * 保存
     * @param doctor
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody DoctorEntity doctor){
		doctorService.save(doctor);
        return R.ok();
    }

    /**
     * 修改
     * @param doctor
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody DoctorEntity doctor){
		doctorService.updateById(doctor);
        return R.ok();
    }

    /**
     * 删除
     * @param dIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] dIds){
		doctorService.removeByIds(Arrays.asList(dIds));
        return R.ok();
    }

}
