package com.qmx.smedicinebox.sys.pccontroller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.qmx.smedicinebox.common.Result;
import com.qmx.smedicinebox.param.PageParams;
import com.qmx.smedicinebox.sys.entity.DailyMedicationPlanEntity;
import com.qmx.smedicinebox.sys.service.DailyMedicationPlanService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.DailyMedicationPlanModifyVo;
import com.qmx.smedicinebox.vo.DailyMedicationPlanSaveVo;
import com.qmx.smedicinebox.vo.UserDailyPlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户用药计划表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-11-07 21:27:10
 */
@RestController
@RequestMapping("pc/dailymedicationplan")
public class DailyMedicationPlanController {
    @Autowired
    private DailyMedicationPlanService dailyMedicationPlanService;

    /**
    * 获取用户的用药计划
    * */
    @GetMapping("/userPlan/{userEntityId}")
    public Result getUserPlanByUserEntityId(@PathVariable Integer userEntityId) {
        List<UserDailyPlanVO> userPlanList = dailyMedicationPlanService.selectUserPlanById(userEntityId);
        return Result.success(userPlanList);
    }
    /**
     * 列表(无分页）
     * @return
     */
    @GetMapping("/list")
    public R list() {
        List<DailyMedicationPlanEntity> list = dailyMedicationPlanService.list();
        return R.ok().put("list", list);
    }

    /**
     * 列表(分页查询)
     * @param params
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody PageParams params) {
        PageUtils page = dailyMedicationPlanService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     * @param dpId
     * @return
     */
    @GetMapping("/info/{dpId}")
    public R info(@RequestParam("dpId") Integer dpId) {
        DailyMedicationPlanEntity dailyMedicationPlan = dailyMedicationPlanService.getById(dpId);
        return R.ok().put("dailyMedicationPlan", dailyMedicationPlan);
    }

    /**
     * 保存
     * @param dailyMedicationPlanSaveVo
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody DailyMedicationPlanSaveVo dailyMedicationPlanSaveVo) {
        Boolean save = dailyMedicationPlanService.save(dailyMedicationPlanSaveVo);
        if (!save) {
            return R.error("保存失败");
        } else {
            return R.ok("保存成功");
        }
    }

    /**
     * 修改
     * @param dailyMedicationPlan
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody DailyMedicationPlanModifyVo dailyMedicationPlan) {
        Boolean i = dailyMedicationPlanService.updateById(dailyMedicationPlan);
        if (!i) {
            return R.error("修改失败");
        }
        return R.ok("修改成功");
    }

    /**
     * 删除
     * @param dpIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] dpIds) {
        dailyMedicationPlanService.removeByIds(Arrays.asList(dpIds));
        return R.ok();
    }
}
