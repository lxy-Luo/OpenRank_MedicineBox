package com.qmx.smedicinebox.sys.pccontroller;

import com.qmx.smedicinebox.dto.EmergencyContactUpdateDto;
import com.qmx.smedicinebox.sys.entity.EmergencyContactEntity;
import com.qmx.smedicinebox.sys.entity.UserEntity;
import com.qmx.smedicinebox.sys.service.EmergencyContactService;
import com.qmx.smedicinebox.sys.service.UserService;
import com.qmx.smedicinebox.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



/**
 * 紧急联系人
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-16 13:49:30
 */
@RestController("EmergencyContactControllerPC")
@RequestMapping("pc/emergencycontact")
public class EmergencyContactController {
    @Autowired
    private EmergencyContactService emergencyContactService;

    @Autowired
    private UserService userService;

    /**
     * 列表
     * @return
     */
    @GetMapping("/list")
    public R list(){
        List<EmergencyContactEntity> list = emergencyContactService.list();
        return R.ok().put("list", list);
    }

    /**
     * 详情
     * @param ecId
     * @return
     */
    @GetMapping("/info/{ecId}")
//    @Operation(summary = "查询相应ec")
    public R info(@PathVariable("ecId") Integer ecId){
		EmergencyContactEntity emergencyContact = emergencyContactService.getById(ecId);
        if(emergencyContact.getEcIsValid().equals(EmergencyContactEntity.UNVALID)){
            return R.ok().put("emergencyContact", null);
        }
        return R.ok().put("emergencyContact", emergencyContact);
    }

    /**
     * 用户紧急联系人
     * @return
     */
    @GetMapping("/userContact")
    public R userContact(@Param("userId") Integer uId){
        UserEntity byId = userService.getById(uId);
        EmergencyContactEntity emergencyContact = emergencyContactService.getById(byId.getUEmergencyContact());
        if(emergencyContact.getEcIsValid().equals(EmergencyContactEntity.UNVALID)){
            return R.ok().put("emergencyContact", null);
        }
        return R.ok().put("emergencyContact", emergencyContact);
    }

    /**
     * 保存
     * @param emergencyContact
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody EmergencyContactEntity emergencyContact){
        emergencyContactService.save(emergencyContact);
        return R.ok();
    }

    /**
     * 修改
     * @param emergencyContact
     * @return
     */
    @PostMapping("/update")
    @Operation(summary = "修改用户信息")
    public R update(@RequestBody EmergencyContactEntity emergencyContact){
        emergencyContactService.updateById(emergencyContact);
        return R.ok();
    }


    /**
     * 删除
     * @param ecIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] ecIds){
		emergencyContactService.removeByIds(Arrays.asList(ecIds));
        return R.ok();
    }



    /**
     * 修改 get
     * @param ecId
     * @param ecName
     * @param ecPhone
     * @param ecEmail
     * @param ecDesc
     * @return
     */
    @GetMapping("/update")
    @Operation(summary = "更新紧急联系人")
    public R updateGet(@RequestParam Integer ecId,
                       @RequestParam String ecName,
                       @RequestParam String ecPhone,
                       @RequestParam String ecEmail,
                       @RequestParam String ecDesc){

        this.emergencyContactService.updatePart(new EmergencyContactUpdateDto(ecId,ecName,ecPhone,ecEmail,null,ecDesc));
        return R.ok("紧急联系人信息更新成功");
    }



}
