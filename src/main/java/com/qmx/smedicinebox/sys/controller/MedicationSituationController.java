package com.qmx.smedicinebox.sys.controller;

import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.sys.entity.MedicationSituationEntity;
import com.qmx.smedicinebox.sys.entity.MedicineEntity;
import com.qmx.smedicinebox.sys.service.MedicationSituationService;
import com.qmx.smedicinebox.utils.DateTimeUtil;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.MSVo;
import com.qmx.smedicinebox.vo.MedicationSituationVo;
import com.qmx.smedicinebox.vo.MessageVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 用药情况表
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-03-26 20:55:57
 */
@RestController
@RequestMapping("smb/medicationsituation")
public class MedicationSituationController {
    @Autowired
    private MedicationSituationService medicationSituationService;
    //IoT参数
    @Autowired
    private UserController userController;
    @Value("${IoT.host}")
    private String host;
    @Value("${IoT.port}")
    private int port;
    @Value("${IoT.username}")
    private String userName;
    @Value("${IoT.password}")
    private String password;

//    /**
//     * 列表
//     */
//    @PostMapping("/listpage")
//    public R listpage(@RequestBody Map<String, Object> params){
//        PageUtils page = medicationSituationService.queryPage(params);
//        return R.ok().put("page", page);
//    }

    /**
     * 保存
     * @param sId
     * @return
     */
    @GetMapping("/info/{sId}")
    public R info(@PathVariable("sId") Integer sId){
		MedicationSituationEntity medicationSituation = medicationSituationService.getById(sId);
        return R.ok().put("medicationSituation", medicationSituation);
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
        System.out.println(s);
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
        //新增IoT数据库操作
        //获取session
        Session session = userController.createSession();
        //查询操作
        medicationSituationService.getDataByDate(session,userId,date);
        //关闭session
        try {
            session.close();
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 获取记录（post）
     * @param msVo
     * @return
     */
    @PostMapping("/msList")
//    @Operation(summary = "获取记录",description = "传入userId和日期字符串")
    public R medicationSituationList(@RequestBody MSVo msVo){
        String datetime = DateTimeUtil.addLeadingZeros(msVo.getDate(), Constant.HYPHEN);
        String s = DateTimeUtil.TransformStringSlashToDefault(datetime);
        R result = medicationSituationService.medicationSituationList(msVo.getUserId(),s);
        return result;
    }

    /**
     * 保存
     * @param medicationSituationVo
     * @return
     */
    @PostMapping("/save")
//    @Operation(summary = "增加记录",description = "传入medicationSituationVo对象")
    public R save(@RequestBody MedicationSituationVo medicationSituationVo){
		medicationSituationService.save(medicationSituationVo);
        //新增IoTDB操作
        //创建Session
        Session session = userController.createSession();
        //数据
        List<Object> values=new ArrayList<>();
        values.add(medicationSituationVo.getDrugId());
        values.add(medicationSituationVo.getAll_number());
        medicationSituationService.insertRecord(session,medicationSituationVo.getUserId(),values);
        //关闭Session
        try {
            session.close();
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        }
        return R.ok();
    }

    /**
     * 批量保存
     * @param medicationSituations
     * @return
     */
    @PostMapping("/saveBatch")
    public R saveBatch(@RequestBody List<MedicationSituationEntity> medicationSituations){
        medicationSituationService.saveBatch(medicationSituations);
        //新增IoTDB操作
        //创建Session
        Session session = userController.createSession();
        //获取用户id和values
        List<Integer> uIds=new ArrayList<>();
        List<List<Object>> valueList=new ArrayList<>();
        for (MedicationSituationEntity medicationSituation : medicationSituations) {
            uIds.add(medicationSituation.getSUser());
            List<Object> value=new ArrayList<>();
            value.add(medicationSituation.getSMedicine());
            value.add(medicationSituation.getSDosing());
            valueList.add(value);
        }
        medicationSituationService.insertRecords(session,uIds,valueList);
        try {
            session.close();
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        }

        return R.ok();
    }


    /**
     * 修改
     * @param medicationSituation
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody MedicationSituationEntity medicationSituation){
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

}
