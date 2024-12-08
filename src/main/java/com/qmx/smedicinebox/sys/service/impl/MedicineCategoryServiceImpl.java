package com.qmx.smedicinebox.sys.service.impl;

import com.qmx.smedicinebox.sys.dao.MedicineDao;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.MedicineCategoryAllList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;

import com.qmx.smedicinebox.sys.dao.MedicineCategoryDao;
import com.qmx.smedicinebox.sys.entity.MedicineCategoryEntity;
import com.qmx.smedicinebox.sys.service.MedicineCategoryService;


@Service("medicineCategoryService")
public class MedicineCategoryServiceImpl extends ServiceImpl<MedicineCategoryDao, MedicineCategoryEntity> implements MedicineCategoryService {
    @Autowired
    private MedicineDao medicineDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MedicineCategoryEntity> page = this.page(
                new Query<MedicineCategoryEntity>().getPage(params),
                new QueryWrapper<MedicineCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R listAll() {
        List<MedicineCategoryEntity> medicineCategoryEntities = this.baseMapper.selectList(null);
        List<MedicineCategoryAllList> mCategory = medicineCategoryEntities.stream().map(item -> {
            QueryWrapper<MedicineEntity> medicineEntityQueryWrapper = new QueryWrapper<>();

            Integer cId = item.getCId();
            medicineEntityQueryWrapper.eq("m_category", cId);
            List<MedicineEntity> medicineEntities = medicineDao.selectList(medicineEntityQueryWrapper);

            MedicineCategoryAllList medicineCategoryAllList = new MedicineCategoryAllList();
            medicineCategoryAllList.setCId(item.getCId());
            medicineCategoryAllList.setCName(item.getCName());
            medicineCategoryAllList.setMedicines(medicineEntities);

            return medicineCategoryAllList;

        }).collect(Collectors.toList());

        return R.ok().put("date",mCategory);
    }

    @Override
    public R deleteAll() {
        int delete = this.baseMapper.delete(null);
        if(delete == 0){
            return R.error("列表为空或删除失败");
        }
        return R.ok();
    }

}