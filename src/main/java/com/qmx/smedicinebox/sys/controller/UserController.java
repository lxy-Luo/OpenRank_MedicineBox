package com.qmx.smedicinebox.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.dto.UserLoginDto;
import com.qmx.smedicinebox.dto.UserRegisterDto;
import com.qmx.smedicinebox.dto.UserVerificationDto;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.sys.service.UserService;
import com.qmx.smedicinebox.utils.DateTimeUtil;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;

import com.qmx.smedicinebox.valid.LoginGroup;

import com.qmx.smedicinebox.vo.UserLoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
@RestController
@RequestMapping("smb/user")
public class UserController {
    @Autowired
    private UserService userService;
    //IoT参数
    @Value("${IoT.host}")
    private String host;
    @Value("${IoT.port}")
    private int port;
    @Value("${IoT.username}")
    private String userName;
    @Value("${IoT.password}")
    private String password;

    /**
     * 新建Session
     * @return session
     */
    public Session createSession(){
        Session session =new Session.Builder()
                .host(host)
                .port(port)
                .username(userName)
                .password(password)
                .build();
        try {
            session.open(false);
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        }
        session.setFetchSize(10000);
        return session;
    }

//    @PostMapping("/listpage")
////    @Operation(summary = "获取所有用户信息（分页）",description = "传入分页信息")
//    public R listpage( @Parameter(description = Constant.Params, in = ParameterIn.QUERY, required = true)
//                           @RequestBody  Map<String, Object> params){
//        PageUtils page = userService.queryPage(params);
//        return R.ok().put("page", page);
//    }

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
     * @param uId
     * @return
     */
    @GetMapping("/info/{uId}")
//    @Operation(summary = "查找用户信息",description = "传入用户id")
    public R info( @PathVariable("uId") Integer uId){
		UserEntity user = userService.getById(uId);
        return R.ok().put("user", user);
    }


    /**
     * 保存
     * @param user
     * @return
     */

    @PostMapping("/save")
//    @Operation(summary = "新增用户",description = "传入用户对象")
    public R save(@Valid @RequestBody UserEntity user){
		userService.save(user);
        //新增IotDB数据库操作
        //数据库是root.medical
        //创建session
        Session session = createSession();
        //传入session和uId创建时间序列
        userService.createTimeSeries(session, user.getUId());
        try {
            session.close();
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        }
        return R.ok();
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @PostMapping("/update")
//    @Operation(summary = "更新用户信息",description = "传入用户对象")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
//    @Operation(summary = "删除用户",description = "传入用户id数组")
    public R delete(@RequestBody Integer[] uIds){
		userService.removeByIds(Arrays.asList(uIds));
        //新增IotDB数据库操作
        //创建Session
        Session session = createSession();
        //根据id删除对应设备
        userService.deleteTimeSeries(session,uIds);
        try {
            session.close();
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        }
        return R.ok();
    }


    /**
     * 登录Post接口
     * @param user
     * @return
     */
    @PostMapping("/logIn")
    @Operation(summary = "登录接口(post请求）",description = "传入user对象进行登录")
    public R login(@RequestBody @Validated(value = LoginGroup.class) UserLoginDto user) {
        R result = userService.logIn(user);
        return result;
    }


    /**
     * 登录Get接口
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/logIn/get/{username}/{password}")
    @Operation(summary = "登录接口(get请求）",description = "传入用户名和密码进行登录")
    public R logIn(@PathVariable("username") String username,
                   @PathVariable("password") String password) {
        R result = userService.logIn(new UserLoginDto(username,password));
        return result;
    }

    /**
     * 发送验证码（注册账号）post
     * @param user
     * @return
     */
    @PostMapping("/register")
//    @Operation(summary = "发送验证码并注册账号")
    public R register(@RequestBody UserRegisterDto user) {
        R register = userService.register(user);
        return register;
    }

    /**
     * 发送验证码（注册账号）get
     * @param uname
     * @param uusername
     * @param upassword
     * @param uemail
     * @return
     */
    @GetMapping("/register")
//    @Operation(summary = "发送验证码并注册账号(get)")
    public R registerGet(@RequestParam String uname,
                         @RequestParam String uusername,
                         @RequestParam String upassword,
                         @RequestParam String uemail) {
        UserRegisterDto userRegisterDto = new UserRegisterDto(uname,uusername,upassword,uemail,null,null);
        R register = userService.register(userRegisterDto);
        return register;
    }

    /**
     * 检验验证码并激活账号post
     * @param user
     * @return
     */
    @PostMapping("verification")
//    @Operation(summary = "检验验证码并激活账号")
    public R verify(@RequestBody UserVerificationDto user) {
        R verification = userService.verification(user);
        return verification;
    }


    /**
     * 检验验证码并激活账号get
     * @param uname
     * @param uusername
     * @param upassword
     * @param uemail
     * @param uverifyCode
     * @return
     */
    @GetMapping("/verification")
//    @Operation(summary = "检验验证码并激活账号(get)")
    public R verifyGet(@RequestParam String uname,
                         @RequestParam String uusername,
                         @RequestParam String upassword,
                         @RequestParam String uemail,
                       @RequestParam String uverifyCode) {
        UserVerificationDto userVerificationDto = new UserVerificationDto(uname,uusername,upassword,uemail,null,null,uverifyCode);
        R verification = userService.verification(userVerificationDto);
        return verification;
    }
}
