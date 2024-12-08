package com.qmx.smedicinebox.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.qmx.smedicinebox.constant.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qmx.smedicinebox.sys.entity.SenderEntity;
import com.qmx.smedicinebox.sys.service.SenderService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;



/**
 * 发送方
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
@RestController
@RequestMapping("sys/sender")
public class SenderController {
    @Autowired
    private SenderService senderService;

    /**
     * 列表
     * @return
     */
    @GetMapping("/list")
    public R list(){
        List<SenderEntity> list = senderService.list();
        return R.ok().put("list",list);
    }


//    @PostMapping("/listpage")
////    @Operation(summary = "获取所有发送方信息（分页）",description = "传入分页信息")
//    public R listpage( @Parameter(description = Constant.Params, in = ParameterIn.QUERY, required = true)
//                       @RequestBody  Map<String, Object> params){
//        PageUtils page = senderService.queryPage(params);
//        return R.ok().put("page", page);
//    }

    /**
     * 信息
     * @param sedId
     * @return
     */
    @GetMapping("/info/{sedId}")
    public R info(@PathVariable("sedId") Integer sedId){
		SenderEntity sender = senderService.getById(sedId);
        return R.ok().put("sender", sender);
    }

    /**
     * 保存
     * @param sender
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody SenderEntity sender){
		senderService.save(sender);

        return R.ok();
    }

    /**
     * 修改
     * @param sender
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody SenderEntity sender){
		senderService.updateById(sender);

        return R.ok();
    }

    /**
     * 删除
     * @param sedIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] sedIds){
		senderService.removeByIds(Arrays.asList(sedIds));
        return R.ok();
    }

}
