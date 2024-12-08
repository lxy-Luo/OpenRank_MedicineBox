package com.qmx.smedicinebox.sys.controller;

import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.sys.entity.MedicationPictureEntity;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.service.MedicationPictureService;
import com.qmx.smedicinebox.sys.service.MedicationSituationService;
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
 * 用药图片
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
@RestController
@RequestMapping("smb/medicationpicture")
public class MedicationPictureController {
    @Autowired
    private MedicationPictureService medicationPictureService;
    @Autowired
    private MedicationSituationService medicationSituationService;

    /**
     * 列表
     * @param pId
     * @return
     */
    @GetMapping("/info/{pId}")
//    @Operation(summary = "获取图片信息",description = "传入图片id")
    public R info(@PathVariable("pId") Integer pId){
		MedicationPictureEntity medicationPicture = medicationPictureService.getById(pId);
        return R.ok().put("medicationPicture", medicationPicture);
    }

    /**
     * 保存
     * @param medicationPicture
     * @return
     */
    @PostMapping("/save")
//    @Operation(summary = "新增图片",description = "传入图片对象")
    public R save(@RequestBody MedicationPictureEntity medicationPicture){
		medicationPictureService.save(medicationPicture);
        return R.ok();
    }

    /**
     * 修改
     * @param medicationPicture
     * @return
     */
    @PostMapping("/update")
//    @Operation(summary = "更新图片信息",description = "传入图片对象")
    public R update(@RequestBody MedicationPictureEntity medicationPicture){
		medicationPictureService.updateById(medicationPicture);
        return R.ok();
    }

    /**
     * 删除
     * @param pIds
     * @return
     */
    @PostMapping("/delete")
//    @Operation(summary = "删除图片",description = "传入图片id数组")
    public R delete(@RequestBody Integer[] pIds){
		medicationPictureService.removeByIds(Arrays.asList(pIds));
        return R.ok();
    }

    /**
     * 获取全部图片
     * @return
     */
    @GetMapping("/list")
//    @Operation(summary = "获取全部图片",description = "无参数")
    public R list(){
        List<MedicationPictureEntity> list = medicationPictureService.list();
        return R.ok().put("list", list);
    }

    /**
     * 上传图片（硬件接口）
     * @param ans
     * @return
     */
    @PostMapping("/bin")
//    @Operation(summary = "上传图片",description = "这个是硬件的接口")
    public R getImgBin(@RequestBody String ans){
        medicationPictureService.getImgBin(ans);
        return R.ok();
    }

}
