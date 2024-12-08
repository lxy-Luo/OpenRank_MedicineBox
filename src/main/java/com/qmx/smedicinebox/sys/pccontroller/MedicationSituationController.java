package com.qmx.smedicinebox.sys.pccontroller;

import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.sys.entity.MedicationSituationEntity;
import com.qmx.smedicinebox.sys.service.MedicationSituationService;
import com.qmx.smedicinebox.utils.DateTimeUtil;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.MSVo;
import com.qmx.smedicinebox.vo.MedicationSituationModifyVo;
import com.qmx.smedicinebox.vo.MedicationSituationVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 用药情况表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
@RestController("MedicationSituationControllerPC")
@RequestMapping("pc/medicationsituation")
public class MedicationSituationController {
    @Autowired
    private MedicationSituationService medicationSituationService;

//    /**
//     * 列表
//     */
//    @PostMapping("/listpage")
//    public R listpage(@RequestBody Map<String, Object> params){
//        PageUtils page = medicationSituationService.queryPage(params);
//        return R.ok().put("page", page);
//    }

    /**
     * info
     * @param sId
     * @return
     */
    @GetMapping("/info/{sId}")
    public R info(@PathVariable("sId") Integer sId){
		MedicationSituationEntity medicationSituation = medicationSituationService.getById(sId);
        return R.ok().put("medicationSituation", medicationSituation);
    }

    /**
     * 保存
     * @param medicationSituationVo
     * @return
     */
    @PostMapping("/save")
//    @Operation(summary = "增加记录",description = "传入medicationSituationVo对象")
    public R save(@RequestBody MedicationSituationVo medicationSituationVo, HttpSession session){
		medicationSituationService.save(medicationSituationVo,session);
        return R.ok();
    }

    /**
     * 修改
     * @param medicationSituation
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody MedicationSituationModifyVo medicationSituation){
		medicationSituationService.updateById(medicationSituation);
        return R.ok();
    }

    /**
     * 删除
     * @param sIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] sIds){
		medicationSituationService.removeByIds(Arrays.asList(sIds));
        return R.ok();
    }

    /**
     * 获取记录（Restful风格）
     * @param userId
     * @param date
     * @return
     */
    @GetMapping("/list/{userId}/{date}")
//    @Operation(summary = "获取记录",description = "传入userId和日期字符串")
    public R medicationSituationList(@PathVariable("userId") Integer userId,
                                     @PathVariable("date") String date){
        String s = DateTimeUtil.addLeadingZeros(date, Constant.HYPHEN);
        R result = medicationSituationService.medicationSituationList(userId, s);
        return result;
    }

    /**
     * 获取记录
     * @param userId
     * @param date
     * @return
     */
    @GetMapping("/list")
//    @Operation(summary = "获取记录",description = "传入userId和日期字符串")
    public R medicationSituationListQuery(@RequestParam Integer userId,
                                          @RequestParam String date){
        String datetime = DateTimeUtil.addLeadingZeros(date, Constant.HYPHEN);
        String s = DateTimeUtil.TransformStringSlashToDefault(datetime);
        R result = medicationSituationService.medicationSituationList(userId, s);
        return result;
    }

    /**
     * 获取用药记录
     * @param userId
     * @param page
     * @return
     */
    @GetMapping("/history/list/{userId}/{page}")
    public R getHistoryList(@PathVariable Integer userId,@PathVariable Integer page){
        return medicationSituationService.getHistory(userId,page);
    }
}
