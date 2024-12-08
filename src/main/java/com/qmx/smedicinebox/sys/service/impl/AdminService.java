package com.qmx.smedicinebox.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.Constants;
import com.qmx.smedicinebox.common.enums.ResultCodeEnum;
import com.qmx.smedicinebox.common.enums.RoleEnum;
import com.qmx.smedicinebox.exception.CustomException;
import com.qmx.smedicinebox.sys.dao.AdminDao;
import com.qmx.smedicinebox.sys.entity.Account;
import com.qmx.smedicinebox.sys.entity.Admin;
import com.qmx.smedicinebox.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value="adminService")
public class AdminService {
    @Resource
    private AdminDao adminDao;
    /*
     * 新增
     */
    public void add(Admin admin) {
        Admin dbAdmin = adminDao.selectByUsername(admin.getUsername());
        if (ObjectUtil.isNotNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(admin.getPassword())) {
            admin.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        //名字为空用用户名代替
        if (ObjectUtil.isEmpty(admin.getName())) {
            admin.setName(admin.getUsername());
        }
        admin.setRole(RoleEnum.ADMIN.name());
        adminDao.insert(admin);
    }
    /*
     * 删除
     */
    public void deleteById(Integer id) {
        adminDao.deleteById(id);
    }

    /*
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            adminDao.deleteById(id);
        }
    }

    /*
     * 修改
     */
    public void updateById(Admin admin) {
        adminDao.updateById(admin);
    }

    /*
     * 根据ID查询
     */
    public Admin selectById(Integer id) {
        return adminDao.selectById(id);
    }

    /*
     * 查询所有
     */
    public List<Admin> selectAll(Admin admin) {
        return adminDao.selectAll(admin);
    }

    /*
     * 分页查询
     */
    public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminDao.selectAll(admin);
        return PageInfo.of(list);
    }

    /*
     * 登录（加密）
     */
    public Account login(Account account) {
        Account dbAdmin = adminDao.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //鉴定
        if (!dbAdmin.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbAdmin.getId() + "-" + RoleEnum.ADMIN.name();
        String token = TokenUtils.createToken(tokenData, dbAdmin.getPassword());
        dbAdmin.setToken(token);
        return dbAdmin;
    }



    /*
     * 修改密码（加密）
     */
    public void updatePassword(Account account) {
        Admin dbAdmin = adminDao.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //校验原密码是否正确
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbAdmin.setPassword(account.getNewPassword());
        adminDao.updateById(dbAdmin);
    }
    
}
