package com.qmx.smedicinebox.sys.pccontroller;

import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.common.Result;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.entity.PCUser;
import com.qmx.smedicinebox.sys.service.MedicineService;
import com.qmx.smedicinebox.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 药品
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
@RestController("MedicineControllerPC")
@RequestMapping("pc/medicine")
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

    @PostMapping("/add")
    public Result add(@RequestBody MedicineEntity medicineEntity) {
        medicineService.save(medicineEntity);
        return Result.success();
    }
    /*
     *单个删除（已测试）
     *  */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        medicineService.removeById(id);
        return Result.success();
    }
    /**
     * 批量删除（已测试）
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {  //  [1,2,3]
        medicineService.removeBatchByIds(ids);
        return Result.success();
    }
    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody MedicineEntity medicineEntity) {
        medicineService.updateById(medicineEntity);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        MedicineEntity medicineEntity =medicineService.getById(id);
        return Result.success(medicineEntity);
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
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(MedicineEntity medicineEntity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MedicineEntity> page = medicineService.selectPage(medicineEntity, pageNum, pageSize);
        return Result.success(page);
    }
}
