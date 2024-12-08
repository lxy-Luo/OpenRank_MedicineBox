package com.qmx.smedicinebox.sys.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qmx.smedicinebox.sys.dao.MedicineDao;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.service.MedicineService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;

import com.qmx.smedicinebox.utils.StringSimilarityUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("medicineService")
public class MedicineServiceImpl extends ServiceImpl<MedicineDao, MedicineEntity> implements MedicineService {

    @Resource
    private MedicineDao medicineDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MedicineEntity> page = this.page(
                new Query<MedicineEntity>().getPage(params),
                new QueryWrapper<MedicineEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public MedicineEntity equalMedicineName(List<String> wordList) {

        HashMap<Double, Integer> stringHashMap = new HashMap<Double, Integer>();

        List<MedicineEntity> medicineEntities = this.baseMapper.selectList(null);

        double maxSt = 0;

        for (int i=0;i<medicineEntities.size();i++){
            String mName = medicineEntities.get(i).getMName();
            double maxSimilarity = 0;

            // 将词添加到数组中
            for (int j = 0; j < wordList.size(); j++) {
                maxSimilarity=maxSimi(maxSimilarity,StringSimilarityUtil.calculateSimilarity(wordList.get(j),mName));
            }

            stringHashMap.put(maxSimilarity,i);
            maxSt=maxSimi(maxSimilarity,maxSt);
        }

        if(maxSt<0.3333){
            return null;
        }
        Integer index = stringHashMap.get(maxSt);

        return medicineEntities.get(index);
    }

    @Override
    public PageInfo<MedicineEntity> selectPage(MedicineEntity medicineEntity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MedicineEntity> list = medicineDao.selectAll(medicineEntity);
        return PageInfo.of(list);
    }


    private double maxSimi(double maxSimilarity, double v) {

        return maxSimilarity>v?maxSimilarity:v;
    }

}