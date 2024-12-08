package com.qmx.smedicinebox.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.vo.MessageList;
import com.qmx.smedicinebox.vo.MessageSaveVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qmx.smedicinebox.sys.entity.MessageEntity;
import com.qmx.smedicinebox.sys.service.MessageService;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.R;



/**
 * 消息
 *
 * @author xiaozhiyong
 * @email xiaozhiyong03@gmail.com
 * @date 2024-04-01 16:41:08
 */
@RestController
@RequestMapping("sys/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

//    /**
//     * 列表
//     */
//    @GetMapping("/list")
//    @Operation(summary = "获取所有消息信息",description = "无参数")
//    public R list(){
//        MessageList messageList = messageService.listDetails();
//        return R.ok().put("list", messageList);
//    }

    /**
     * 用户所有消息
     * @param userId
     * @return
     */
    @GetMapping("/list/{userId}")
    @Operation(summary = "获取所有消息信息",description = "userId")
    public R list(@PathVariable("userId") Integer userId){
        MessageList messageList = messageService.listDetails(userId);
        return R.ok().put("list", messageList);
    }

    /**
     * 获取未读消息数量
     * @param userId
     * @return
     */
    @GetMapping("/getUnreadTotal/{userId}")
    @Operation(summary = "获取未读消息数量",description = "无参数")
    public R getUnReadTotal(@PathVariable("userId") Integer userId){
        Integer total = messageService.getUnReadTotal(userId);
        return R.ok().put("unreadTotal", total);
    }

    /**
     * 消息内容
     * @param msgId
     * @return
     */
    @GetMapping("/info/{msgId}")
    public R info(@PathVariable("msgId") Integer msgId){
		MessageEntity message = messageService.getById(msgId);
        return R.ok().put("message", message);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Operation(summary = "保存信息",description = "传入userid和发送内容")
    public R save(@RequestBody MessageSaveVo message){
		messageService.save(message);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@Valid @RequestBody MessageEntity message){
		messageService.updateById(message);
        return R.ok();
    }

    /**
     * 更新消息已读状态（传入消息id和新的状态）
     * @param msgId
     * @param isRead
     * @return
     */
    @GetMapping("/update/{msgId}/{isRead}")
    @Operation(summary = "更新消息已读状态",description = "传入消息id和新的状态")
    public R updateIsRead(@PathVariable("msgId") Integer msgId,
                          @Min(0) @Max(1)@PathVariable("isRead") Integer isRead){
        R result = messageService.updateIsRead(msgId, isRead);
        return result;
    }

    /**
     * 已读全部
     * @param userId
     * @return
     */
    @GetMapping("/update/{userId}")
    @Operation(summary = "全部已读",description = "传入用户id")
    public R updateIsReadAll(@PathVariable("userId") Integer userId){
        R result = messageService.updateIsReadAll(userId);

        return result;
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @Operation(summary = "删除记录",description = "消息的ID数组")
    public R delete(@RequestBody Integer[] msgIds){
        messageService.removeByIds(Arrays.asList(msgIds));
        return R.ok();
    }

    /**
     * 删除消息
     * @param msgId
     * @return
     */
    @GetMapping("/deleteOne/{msgId}")
    @Operation(summary = "删除一条记录",description = "消息ID")
    public R deleteOne(@PathVariable("msgId") Integer msgId){
        boolean b = messageService.removeById(msgId);
        if(!b){
            return R.error("消息不存在或消息记录删除失败");
        }
        return R.ok();
    }

    /**
     * 一键删除
     * @param userId
     * @return
     */
    @GetMapping("/deleteAll/{userId}")
    @Operation(summary = "一键删除",description = "无参数")
    public R deleteAll(@PathVariable("userId") Integer userId){
        R result = messageService.deleteAll(userId);
        return result;
    }

}
