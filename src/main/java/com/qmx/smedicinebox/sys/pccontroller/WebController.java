package com.qmx.smedicinebox.sys.pccontroller;

import cn.hutool.core.util.ObjectUtil;
import com.qmx.smedicinebox.common.Result;
import com.qmx.smedicinebox.common.enums.ResultCodeEnum;
import com.qmx.smedicinebox.common.enums.RoleEnum;
import com.qmx.smedicinebox.sys.entity.Account;
import com.qmx.smedicinebox.sys.service.impl.AdminService;
import com.qmx.smedicinebox.sys.service.impl.PCUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pc")
public class WebController {
    @Resource
    private AdminService adminService;
    @Resource
    private PCUserService pcUserService;
    /*
    * 登录*/
    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        //排除确实参数情况
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        //判断登录者类型
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            account = adminService.login(account);
        }else if (RoleEnum.USER.name().equals(account.getRole())) {
            account = pcUserService.login(account);
        }else{
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        return Result.success(account);
    }
}
