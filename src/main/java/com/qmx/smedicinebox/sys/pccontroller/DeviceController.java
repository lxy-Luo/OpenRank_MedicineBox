package com.qmx.smedicinebox.sys.pccontroller;

import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.common.Result;
import com.qmx.smedicinebox.sys.entity.DeviceEntity;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.service.DeviceService;
import com.qmx.smedicinebox.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



/**
 * 设备
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-27 09:29:58
 */
@RestController("DeviceControllerPC")
@RequestMapping("pc/device")
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


    @PostMapping("/add")
    public Result add(@RequestBody DeviceEntity deviceEntity) {
        deviceService.save(deviceEntity);
        return Result.success();
    }
    /*
     *单个删除（已测试）
     *  */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        deviceService.removeById(id);
        return Result.success();
    }
    /**
     * 批量删除（已测试）
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {  //  [1,2,3]
        deviceService.removeBatchByIds(ids);
        return Result.success();
    }
    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody DeviceEntity deviceEntity) {
        deviceService.updateById(deviceEntity);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        DeviceEntity deviceEntity =deviceService.getById(id);
        return Result.success(deviceEntity);
    }



    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(DeviceEntity deviceEntity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<DeviceEntity> page = deviceService.selectPage(deviceEntity, pageNum, pageSize);
        return Result.success(page);
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

}
