package com.qmx.smedicinebox.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.vo.MedicineCategoryAllList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qmx.smedicinebox.sys.entity.MedicineCategoryEntity;
import com.qmx.smedicinebox.sys.service.MedicineCategoryService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;



/**
 *  药品分类
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@RestController
@RequestMapping("sys/medicinecategory")
public class MedicineCategoryController {
    @Autowired
    private MedicineCategoryService medicineCategoryService;

//    /**
//     * 列表
//     */
//    @PostMapping("/listpage")
//    @Operation(summary = "获取所有药品分类信息（分页）",description = "传入分页信息")
//    public R listpage(@Parameter(description = Constant.MessageParams, in = ParameterIn.QUERY, required = true)
//            @RequestParam Map<String, Object> params){
//        PageUtils page = medicineCategoryService.queryPage(params);
//        return R.ok().put("page", page);
//    }

    /**
     * 信息
     * @param cId
     * @return
     */
    @GetMapping("/info/{cId}")
    public R info(@PathVariable("cId") Integer cId){
		MedicineCategoryEntity medicineCategory = medicineCategoryService.getById(cId);
        return R.ok().put("medicineCategory", medicineCategory);
    }

    /**
     * 获取药品分类信息
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "获取药品分类信息",description = "无参数")
    public R list(){
        List<MedicineCategoryEntity> list = medicineCategoryService.list();
        return R.ok().put("list", list);
    }

    /**
     * 获取药品分类及其所有药品信息
     * @return
     */
    @GetMapping("/listAll")
    @Operation(summary = "获取药品分类及其药品信息",description = "无参数")
    public R listAll(){
        R result = medicineCategoryService.listAll();
        return result;
    }

    /**
     * 保存
     * @param medicineCategory
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody MedicineCategoryEntity medicineCategory){
		medicineCategoryService.save(medicineCategory);
        return R.ok();
    }

    /**
     * 修改
     * @param medicineCategory
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody MedicineCategoryEntity medicineCategory){
		medicineCategoryService.updateById(medicineCategory);
        return R.ok();
    }

    /**
     * 删除
     * @param cIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] cIds){
		medicineCategoryService.removeByIds(Arrays.asList(cIds));
        return R.ok();
    }

    /**
     * 一键删除
     * @return
     */
    @GetMapping("/deleteAll")
    @Operation(summary = "一键删除",description = "无参数")
    public R deleteAll(){
        R result = medicineCategoryService.deleteAll();
        return result;
    }
}
