package com.qmx.smedicinebox.sys.pccontroller;

import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.common.Result;
import com.qmx.smedicinebox.sys.entity.PCUser;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.sys.service.UserService;
import com.qmx.smedicinebox.sys.service.impl.PCUserService;
import com.qmx.smedicinebox.utils.R;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* 用户身份相关接口*/
@RestController
@RequestMapping("/pc/user")
public class PCUserController {
    @Resource
    private PCUserService pcUserService;
    @Resource
    private UserService userService;

    /*
    * 根据设备管理员绑定的设备id获取对应设备下的用户*/
    @GetMapping("/selectUserByDeviceId/{deviceId}")
    public Result selectDeviceByPCUserId(@PathVariable Integer deviceId) {
        List<UserEntity> UserEntityList = userService.selectUserEntityListByDeviceId(deviceId);
        return Result.success(UserEntityList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody PCUser pcUser) {
        pcUserService.add(pcUser);
        return Result.success();
    }
    /*
    *单个删除（已测试）
    *  */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        pcUserService.deleteById(id);
        return Result.success();
    }
    /**
     * 批量删除（已测试）
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {  //  [1,2,3]
        pcUserService.deleteBatch(ids);
        return Result.success();
    }
    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody PCUser pcUser) {
        pcUserService.updateById(pcUser);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        PCUser pcUser = pcUserService.selectById(id);
        return Result.success(pcUser);
    }
    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(PCUser pcUser) {
        List<PCUser> list = pcUserService.selectAll(pcUser);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(PCUser pcUser,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<PCUser> page = pcUserService.selectPage(pcUser, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查看pc端管理员管理的用户
     * @return
     */
    @GetMapping("/listAdminManagedUsers")
    public R listAdminManagedUsers(@Param("pcUser") PCUser pcUser) {
        R result = pcUserService.listAdminManagedUsers(pcUser);
        return result;
    }

}
