package com.qmx.smedicinebox.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmx.smedicinebox.dto.UserLoginDto;
import com.qmx.smedicinebox.dto.UserRegisterDto;
import com.qmx.smedicinebox.dto.UserVerificationDto;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;

import com.qmx.smedicinebox.vo.UserEntityInfoVO;
import com.qmx.smedicinebox.vo.UserSaveVo;
import jakarta.servlet.http.HttpSession;
import org.apache.iotdb.session.Session;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 用户表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:58
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R register(UserRegisterDto user);

    R verification(UserVerificationDto user);

    R getAvatar(Integer currentUserId);

    R updateAvatar(Integer currentUserId, MultipartFile avatarFile);

    R logIn(UserLoginDto user);

    R bindAccountWithFaceScan(String hex);

    R logIn(String json);

    List<UserEntity> selectUserEntityListByDeviceId(Integer deviceId);

    UserEntityInfoVO selectUserInfoByUserId(Integer userId);

    //创建时间序列
    R createTimeSeries(Session session, Integer uId);

    //根据uId删除时间序列
    R deleteTimeSeries(Session session,Integer[] uIds);

}

