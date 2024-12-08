package com.qmx.smedicinebox.sys.pccontroller;

import com.qmx.smedicinebox.sys.entity.SenderEntity;
import com.qmx.smedicinebox.sys.service.SenderService;
import com.qmx.smedicinebox.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



/**
 * 发送方
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
@RestController("SenderControllerPC")
@RequestMapping("pc/sender")
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
