package com.qmx.smedicinebox.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.Constants;
import com.qmx.smedicinebox.common.enums.ResultCodeEnum;
import com.qmx.smedicinebox.common.enums.RoleEnum;
import com.qmx.smedicinebox.dto.PCUserInfoDto;
import com.qmx.smedicinebox.exception.CustomException;
import com.qmx.smedicinebox.sys.dao.DeviceUserRelationDao;
import com.qmx.smedicinebox.sys.dao.PCUserDao;
import com.qmx.smedicinebox.sys.dao.UserDao;
import com.qmx.smedicinebox.sys.entity.Account;
import com.qmx.smedicinebox.sys.entity.DeviceUserRelationEntity;
import com.qmx.smedicinebox.sys.entity.DeviceEntity;
import com.qmx.smedicinebox.sys.entity.PCUser;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.sys.service.DeviceUserRelationService;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.sys.service.DeviceService;
import com.qmx.smedicinebox.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service("PCUserService")
public class PCUserService {
    @Resource
    private PCUserDao pcUserdao;
    @Resource
    private DeviceServiceImpl deviceServiceImpl;

    /*
    *查询当前设备下的所有用户
    * */
    public List<DeviceEntity> selectDeviceById(Integer id) {
        return deviceServiceImpl.selectDeviceById(id);
    }

    @Autowired
    private DeviceUserRelationDao deviceUserRelationDao;

    @Autowired
    private UserDao userDao;


    public void add(PCUser pcUser) {
        //1.判断用户是否重复
        PCUser dbUser = pcUserdao.selectByUsername(pcUser.getUsername());
        if(dbUser!=null){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if(ObjectUtil.isEmpty(pcUser.getPassword())){
            pcUser.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if(pcUser.getName()==null){
            pcUser.setName(pcUser.getUsername());
        }
        pcUser.setRole(RoleEnum.USER.name());  //角色为User对应序数

        pcUserdao.insert(pcUser);
    }

    public void deleteById(Integer id) {
        pcUserdao.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public void updateById(PCUser pcUser) {
        pcUserdao.updateById(pcUser);
    }

    /*
    * 查询用户信息*/
    public PCUser selectById(Integer id) {
        return pcUserdao.selectById(id);
    }

    public List<PCUser> selectAll(PCUser pcUser) {
        return pcUserdao.selectAll(pcUser);
    }

    public PageInfo<PCUser> selectPage(PCUser pcUser, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PCUser> list = pcUserdao.selectAll(pcUser);
        return PageInfo.of(list);
    }

    /*
    * 用户登录逻辑
    * */
    public Account login(Account account) {
        Account dbUser = pcUserdao.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!dbUser.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    /*
     * 注册用户
     */
    public void register(Account account) {
        PCUser pcUser = new PCUser();
        BeanUtils.copyProperties(account, pcUser);
        pcUser.setPassword(pcUser.getPassword());
        this.add(pcUser);
    }

    /*
    * 修改用户密码
    * */
    public void updatePassword(Account account) {
        PCUser dbUser = pcUserdao.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //进行数据对比
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbUser.setPassword(account.getNewPassword());
        pcUserdao.updateById(dbUser);
    }

    /**
     * 获取管理员管理的用户列表
     * @return
     */
    public R listAdminManagedUsers(PCUser pcUser) {
        List<DeviceUserRelationEntity> deviceUserRelationEntities = deviceUserRelationDao.selectList(new QueryWrapper<DeviceUserRelationEntity>().eq("dur_device", pcUser.getDevice()));
        List<Integer> userList = deviceUserRelationEntities.stream().map(deviceUserRelationEntity -> deviceUserRelationEntity.getDurUser()).collect(Collectors.toList());
        List<UserEntity> userEntities = userDao.selectByIds(userList);
        List<PCUserInfoDto> collect = userEntities.stream().map(userEntity -> {
            PCUserInfoDto pcUserInfoDto = new PCUserInfoDto();
            BeanUtils.copyProperties(userEntity, pcUserInfoDto);
            return pcUserInfoDto;
        }).collect(Collectors.toList());

        return R.ok().put("list",collect);
    }


}
