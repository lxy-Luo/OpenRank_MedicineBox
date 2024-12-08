package com.qmx.smedicinebox.sys.pccontroller;
import com.qmx.smedicinebox.common.Result;
import com.qmx.smedicinebox.sys.dao.DeviceDao;
import com.qmx.smedicinebox.sys.dao.DeviceUserRelationDao;
import com.qmx.smedicinebox.sys.dao.UserDao;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.sys.service.EmergencyContactService;
import com.qmx.smedicinebox.sys.service.UserService;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.UserEntityInfoVO;
import com.qmx.smedicinebox.vo.UserUpdateVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;


/**
 * 用户表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
@RestController("UserControllerPC")
@RequestMapping("/pc/smbUser")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private DeviceUserRelationDao deviceUserRelationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private EmergencyContactService emergencyContactService;

    /*
    * 根据用户id查询用户相关信息和联系人信息*/
    @GetMapping("/selectUserInfoByUserId/{userEntityId}")
    public Result selectUserInfoByUserId(@PathVariable Integer userEntityId){
        UserEntityInfoVO userEntityInfoVO = userService.selectUserInfoByUserId(userEntityId);
        return Result.success(userEntityInfoVO);
    }



    /**
     * 列表
     * @return
     */
    @GetMapping("/list")
//    @Operation(summary = "获取所有用户信息",description = "无参数")
    public R list(){
        List<UserEntity> list = userService.list();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     * @return
     */
    @GetMapping("/info")
//    @Operation(summary = "查找用户信息",description = "传入用户id")
    public R info(@Param("userId") Integer uId){
		UserEntity user = userService.getById(uId);
        return R.ok().put("user", user);
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
//    @Operation(summary = "更新用户信息",description = "传入用户对象")
    public R update(@RequestBody UserUpdateVo user){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userService.updateById(userEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
//    @Operation(summary = "删除用户",description = "传入用户id数组")
    public R delete(@RequestBody Integer[] uIds){
		userService.removeByIds(Arrays.asList(uIds));
        return R.ok();
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    @GetMapping("/getAvatar")
    public R getAvatar(@Param("userId") Integer uId) {
        // 这里实现获取用户头像的逻辑
        R result = userService.getAvatar(uId);
        return result;
    }


    /**
     * 更新用户头像
     * @param avatarFile
     * @return
     */
    @PostMapping("/updateAvatar")
    public R updateAvatar(@RequestParam("avatar") MultipartFile avatarFile, @RequestParam("userId") Integer uId) {
        R result = userService.updateAvatar(uId,avatarFile);
        return result;
    }

}
