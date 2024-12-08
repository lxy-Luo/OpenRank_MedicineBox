package com.qmx.smedicinebox.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.dto.DailyMedicationPlanHardWareDto;
import com.qmx.smedicinebox.param.PageParams;
import com.qmx.smedicinebox.sys.dao.DailyMedicationPlanDao;
import com.qmx.smedicinebox.sys.dao.MedicineDao;
import com.qmx.smedicinebox.sys.dao.MessageDao;
import com.qmx.smedicinebox.sys.dao.UserDao;
import com.qmx.smedicinebox.sys.entity.DailyMedicationPlanEntity;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.entity.MessageEntity;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.sys.service.DailyMedicationPlanService;
import com.qmx.smedicinebox.sys.service.DeviceUserRelationService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.DailyMedicationPlanModifyVo;
import com.qmx.smedicinebox.vo.DailyMedicationPlanSaveVo;
import lombok.extern.slf4j.Slf4j;
import com.qmx.smedicinebox.vo.UserDailyPlanVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("dailyMedicationPlanService")
public class DailyMedicationPlanServiceImpl extends ServiceImpl<DailyMedicationPlanDao, DailyMedicationPlanEntity> implements DailyMedicationPlanService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private MedicineDao medicineDao;

    @Autowired
    private DailyMedicationPlanDao dailyMedicationPlanDao;

    @Autowired
    private MessageDao messageDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DailyMedicationPlanEntity> page = this.page(
                new Query<DailyMedicationPlanEntity>().getPage(params),
                new QueryWrapper<DailyMedicationPlanEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(PageParams params) {
        QueryWrapper<DailyMedicationPlanEntity> queryWrapper = new QueryWrapper<>();

        // 添加模糊匹配条件
        if (StringUtils.isNotEmpty(params.getSearch()) && StringUtils.isNotEmpty(params.getSearchField())) {
            queryWrapper.like(params.getSearchField(), params.getSearch());
        }

        IPage<DailyMedicationPlanEntity> page = this.page(
                new Query<DailyMedicationPlanEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public Boolean save(DailyMedicationPlanSaveVo dailyMedicationPlanSaveVo) {
        Integer dpDosage = dailyMedicationPlanSaveVo.getDpDosage();
        Integer dpMedicine = dailyMedicationPlanSaveVo.getDpMedicine();
        Integer dpUser = dailyMedicationPlanSaveVo.getDpUser();
        int i = this.baseMapper.insertDailyMedicationPlan(new DailyMedicationPlanEntity(dpUser, dpDosage, dpMedicine));
        return i > 0;
    }

    @Override
    public Boolean updateById(DailyMedicationPlanModifyVo dailyMedicationPlan) {
        DailyMedicationPlanEntity dailyMedicationPlanEntity = new DailyMedicationPlanEntity();
        BeanUtils.copyProperties(dailyMedicationPlan, dailyMedicationPlanEntity);
        int i = this.baseMapper.updateById(dailyMedicationPlanEntity);
        return i> 0;
    }

    @Override
    public R returnDailyMedicationPlan(String uIdentityId) {
        // 根据身份识别id查询用户信息
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<UserEntity>().eq("u_identity_id", uIdentityId));
        if (userEntity == null) {
            log.info("用户不存在");
            System.out.println("用户不存在");
            return R.error("用户不存在");
        }

        // 根据用户id查询该用户的所有日常用药计划
        List<DailyMedicationPlanEntity> DMPlist = this.baseMapper.selectList(new QueryWrapper<DailyMedicationPlanEntity>().eq("dp_user", userEntity.getUId()));
        if (DMPlist == null || DMPlist.size() == 0) {
            log.info("今日计划为空");
            System.out.println("今日计划为空");
            return R.error("今日计划为空");
        }

        List<DailyMedicationPlanHardWareDto> collect = DMPlist.stream().map(dailyMedicationPlanEntity -> {
            Integer dpMedicine = dailyMedicationPlanEntity.getDpMedicine();
            System.out.println("dpMedicine:" + dpMedicine);
            MedicineEntity medicineEntity = medicineDao.selectById(dpMedicine);
            return new DailyMedicationPlanHardWareDto(dailyMedicationPlanEntity.getDpDosage(),
                    medicineEntity.getMName(),
                    medicineEntity.getMDesc(),
                    dailyMedicationPlanEntity.getDpStatus());
        }).collect(Collectors.toList());
        return R.ok().put("name",userEntity.getUName()).put("data", collect);
    }

    @Override
    public R updateById(String userIdentityId, Integer mId) {
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<UserEntity>().eq("u_identity_id", userIdentityId));
        if (userEntity == null) {
            return R.error("用户不存在");
        }

        Integer uId = userEntity.getUId();
        List<DailyMedicationPlanEntity> dpUser = dailyMedicationPlanDao.selectList(new QueryWrapper<DailyMedicationPlanEntity>().eq("dp_user", uId));
        /**
         * 计划一：如果没有在计划中查找到药瓶，则返回错误信息
         *
         */
        /**
        if (dpUser == null || dpUser.size() == 0) {
            System.out.println("今日计划为空");
            return R.error("今日计划为空");
        }

        int changeNum = 0;
        for (DailyMedicationPlanEntity dailyMedicationPlanEntity : dpUser) {
            if (!dailyMedicationPlanEntity.getDpMedicine().equals(mId)) {
                continue;
            }
            dailyMedicationPlanEntity.setDpMedicine(mId);
            dailyMedicationPlanDao.updateById(dailyMedicationPlanEntity);
            changeNum++;
        }

        if(changeNum == 0) {
            System.out.println("今日计划没有这个药品，您是不是拿错了");
            return R.error("今日计划没有这个药品，您是不是拿错了");
        }
         **/
        /**
         * 计划二：如果在计划中为查找到药品，程序继续进行，但是向用户发送邮件，警告其可能拿错了药品
         */
        if (dpUser == null || dpUser.size() == 0) {
            System.out.println("今日计划为空");
            messageDao.baseInsert(new MessageEntity(new Date(), MessageEntity.NOTREAD, Constant.SENDER_APP, userEntity.getUId(), "您的今日计划为空，请新增每日用药计划"));
            return R.ok("今日计划为空");
        }

        int changeNum = 0;
        for (DailyMedicationPlanEntity dailyMedicationPlanEntity : dpUser) {
            if (!dailyMedicationPlanEntity.getDpMedicine().equals(mId)) {
                continue;
            }
            dailyMedicationPlanEntity.setDpMedicine(mId);
            dailyMedicationPlanDao.updateById(dailyMedicationPlanEntity);
            changeNum++;
        }

        if(changeNum == 0) {
            System.out.println("今日计划没有这个药品，您是不是拿错了");
            messageDao.baseInsert(new MessageEntity(new Date(), MessageEntity.NOTREAD, Constant.SENDER_APP, userEntity.getUId(), "今日计划没有这个药品，您是不是拿错了"));
            return R.ok("今日计划没有这个药品，您是不是拿错了");
        }

        return R.ok();
    }

    /*
    * 获取用户用药计划
    * */
    @Override
    public List<UserDailyPlanVO> selectUserPlanById(Integer userEntityId) {
        List<DailyMedicationPlanEntity> userPlanlist = dailyMedicationPlanDao.selectList(new QueryWrapper<DailyMedicationPlanEntity>().eq("dp_user", userEntityId));
        ArrayList<UserDailyPlanVO> userDailyPlanVOList = new ArrayList<>();
        for (DailyMedicationPlanEntity dp : userPlanlist) {
            UserDailyPlanVO userDailyPlanVO = new UserDailyPlanVO();
            userDailyPlanVO.setDosage(dp.getDpDosage()); //插入药品剂量
            MedicineEntity medicineEntity = medicineDao.selectById(dp.getDpMedicine());
            userDailyPlanVO.setMedicationName(medicineEntity.getMName()); //插入药品名称
            userDailyPlanVO.setMedicationImage(medicineEntity.getMPic()); //插入药品图片
            userDailyPlanVO.setMedicationIntroduction(medicineEntity.getMDesc()); //插入药品介绍
            userDailyPlanVO.setMedicationUnit(medicineEntity.getMUnit()); //插入药品单位
            userDailyPlanVO.setMedicationType(medicineEntity.getMCname()); //插入药品类型
            userDailyPlanVOList.add(userDailyPlanVO);
        }
        return userDailyPlanVOList;
    }

}