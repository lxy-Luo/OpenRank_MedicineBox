package com.qmx.smedicinebox.config;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.qmx.smedicinebox.Constants;
import com.qmx.smedicinebox.common.enums.ResultCodeEnum;
import com.qmx.smedicinebox.common.enums.RoleEnum;
import com.qmx.smedicinebox.exception.CustomException;
import com.qmx.smedicinebox.sys.entity.Account;
import com.qmx.smedicinebox.sys.service.impl.AdminService;
import com.qmx.smedicinebox.sys.service.impl.PCUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);
    @Resource
    private PCUserService pcUserService;
    @Resource
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从http请求的header中获取token
        String token = request.getHeader(Constants.TOKEN);
        if (ObjectUtil.isEmpty(token)) {
            // 如果没拿到，从参数里再拿一次
            token = request.getParameter(Constants.TOKEN);
        }
        // 2. 开始执行认证
        if (ObjectUtil.isEmpty(token)) {
            throw new CustomException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
        Account account = null;
        try {
            // 解析token获取存储的数据
            String userRole = JWT.decode(token).getAudience().get(0);
            String userId = userRole.split("-")[0];
            String role = userRole.split("-")[1];
            // 根据userId查询数据库(管理员和用户)
            if (RoleEnum.ADMIN.name().equals(role)) {
                account = adminService.selectById(Integer.valueOf(userId));
            }else if (RoleEnum.USER.name().equals(role)) {
                account = pcUserService.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        if (ObjectUtil.isNull(account)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        try {
            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        return true;
    }
}