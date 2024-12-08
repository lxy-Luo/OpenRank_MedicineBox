package com.qmx.smedicinebox.sys.controller;

import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.service.MedicineService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 药品
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
@RestController
@RequestMapping("smb/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

//    /**
//     * 列表
//     */
//    @PostMapping("/listpage")
//    @Operation(summary = "获取所有药品信息（分页）",description = "传入分页信息")
//    public R listpage(@Parameter(description = Constant.MessageParams, in = ParameterIn.QUERY, required = true)
//            @RequestParam Map<String, Object> params){
//        PageUtils page = medicineService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }

    /**
     * 获取药品所有信息（无分页）
     * @return
     */
    @GetMapping("/list")
//    @Operation(summary = "获取药品所有信息",description = "无参数")
    public R list(){
        List<MedicineEntity> list = medicineService.list();
        return R.ok().put("list", list);
    }


    /**
     * 信息
     * @param mId
     * @return
     */
    @GetMapping("/info/{mId}")
    public R info(@PathVariable("mId") Integer mId){
		MedicineEntity medicine = medicineService.getById(mId);
        return R.ok().put("medicine", medicine);
    }

    /**
     * 保存
     * @param medicine
     * @return
     */
    @PostMapping("/save")
    @Operation(summary = "新增药品",description = "药品信息")
    public R save(@RequestBody MedicineEntity medicine){
		medicineService.save(medicine);
        return R.ok();
    }


    /**
     * 修改
     * @param medicine
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody MedicineEntity medicine){
		medicineService.updateById(medicine);
        return R.ok();
    }

    /**
     * 删除
     * @param mIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] mIds){
		medicineService.removeByIds(Arrays.asList(mIds));
        return R.ok();
    }

}
