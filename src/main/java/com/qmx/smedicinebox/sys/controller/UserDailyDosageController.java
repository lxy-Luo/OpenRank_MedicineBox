package com.qmx.smedicinebox.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qmx.smedicinebox.sys.entity.UserDailyDosageEntity;
import com.qmx.smedicinebox.sys.service.UserDailyDosageService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;



/**
 * 用户每日剂量表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 08:02:00
 */
@RestController
@RequestMapping("sys/userdailydosage")
public class UserDailyDosageController {
    @Autowired
    private UserDailyDosageService userDailyDosageService;

    /**
     * 信息
     * @param ddUser
     * @return
     */
    @GetMapping("/info/{ddUser}")
    public R info(@PathVariable("ddUser") Integer ddUser){
		UserDailyDosageEntity userDailyDosage = userDailyDosageService.getById(ddUser);
        return R.ok().put("userDailyDosage", userDailyDosage);
    }

    /**
     * 保存
     * @param userDailyDosage
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody UserDailyDosageEntity userDailyDosage){
		userDailyDosageService.save(userDailyDosage);
        return R.ok();
    }

    /**
     * 修改
     * @param userDailyDosage
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody UserDailyDosageEntity userDailyDosage){
		userDailyDosageService.updateById(userDailyDosage);
        return R.ok();
    }

    /**
     * 删除
     * @param ddUsers
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] ddUsers){
		userDailyDosageService.removeByIds(Arrays.asList(ddUsers));
        return R.ok();
    }

}
