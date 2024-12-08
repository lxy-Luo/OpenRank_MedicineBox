package com.qmx.smedicinebox.sys.service.impl;

import com.qmx.smedicinebox.constant.Constant;
import com.qmx.smedicinebox.sys.entity.*;
import com.qmx.smedicinebox.sys.service.SenderService;
import com.qmx.smedicinebox.sys.service.UserService;
import com.qmx.smedicinebox.utils.DateTimeUtil;
import com.qmx.smedicinebox.utils.R;
import com.qmx.smedicinebox.vo.MessageList;
import com.qmx.smedicinebox.vo.MessageSaveVo;
import com.qmx.smedicinebox.vo.MessageVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmx.smedicinebox.utils.PageUtils;
import com.qmx.smedicinebox.utils.Query;

import com.qmx.smedicinebox.sys.dao.MessageDao;
import com.qmx.smedicinebox.sys.service.MessageService;


@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {
    @Autowired
    private SenderService senderService;

    @Autowired
    private UserService userService;

    @Resource
    private MessageDao messageDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<MessageEntity> wapper = new QueryWrapper<>();

        if(params.get(Constant.ISREAD) != null){
            Long isRead = Long.parseLong((String)params.get(Constant.ISREAD));
            if(Objects.nonNull(isRead)){
                wapper.eq("msg_is_read",isRead);
            }
        }

        IPage<MessageEntity> page = this.page(
                new Query<MessageEntity>().getPage(params),
                wapper
        );

        return new PageUtils(page);
    }

    @Override
    public MessageList listDetails() {
        List<MessageEntity> messageEntities = this.baseMapper.selectList(null);

        Integer unReadTotal = 0;
        for(MessageEntity item:messageEntities){
            if(item.getMsgIsRead() == Constant.MSG_NOT_READ){
                unReadTotal++;
            }
        }

        List<MessageVo> messageVos = messageEntities.stream().map(item -> {
            SenderEntity byId = senderService.getById(item.getMsgSender());
            String sender = Constant.SENDER_DEFAULT;
            if (Objects.nonNull(byId)) {
                sender = byId.getSedName();
            }

            MessageVo messageVo = new MessageVo();
            messageVo.setId(item.getMsgId());
            messageVo.setContent(item.getMsgContent());
            messageVo.setDate(DateTimeUtil.splitDateNotYear(item.getMsgDateTime(),Constant.SLASH));
            messageVo.setTime(DateTimeUtil.splitTimeNotSecond(item.getMsgDateTime(),Constant.COLON));
            messageVo.setSender(sender);
            messageVo.setIsRead(item.getMsgIsRead());

            return messageVo;
        }).collect(Collectors.toList());

        MessageList messageVo = new MessageList();
        messageVo.setMessages(messageVos);
        messageVo.setTotal(messageEntities.size());
        messageVo.setUnreadTotal(unReadTotal);

        return messageVo;
    }

    @Override
    public R updateIsRead(Integer msgId, Integer isRead) {
        if(isRead != Constant.MSG_NOT_READ && isRead != Constant.MSG_READ){
            return R.error("isRead参数只能为0或者1");
        }

        MessageEntity messageEntity = this.baseMapper.selectById(msgId);
        if(Objects.isNull(msgId)){
            return R.error("未找到相应message");
        }

        messageEntity.setMsgIsRead(isRead);
        int update = this.baseMapper.updateById(messageEntity);
        if(update == 0){
            return R.error("更新失败，请联系管理员");
        }

        return R.ok();
    }

    @Override
    public R updateIsReadAll(Integer userId) {
        QueryWrapper<MessageEntity> wapper= new QueryWrapper<>();
        wapper.eq("msg_recipient", userId);
        List<MessageEntity> messageEntities = this.baseMapper.selectList(wapper);
        List<Integer> messageEntityIds = messageEntities.stream().map(item -> {
            return item.getMsgId();
        }).collect(Collectors.toList());

        Integer i = this.baseMapper.updateBatchIds(messageEntityIds);
        if(i == 0){
            return R.error("更新失败，请联系管理员");
        }

        return R.ok();
    }

    @Override
    public R deleteAll(Integer userId) {
        QueryWrapper<MessageEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("msg_recipient",userId);
        int delete = this.baseMapper.delete(wrapper);
        if(delete == 0){
            return R.error("列表为空或删除失败");
        }
        return R.ok();
    }

    @Override
    public void save(MessageSaveVo message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMsgIsRead(MessageEntity.NOTREAD);
        messageEntity.setMsgContent(message.getContent());
        messageEntity.setMsgRecipient(message.getUserId());
        messageEntity.setMsgDateTime(new Date());
        messageEntity.setMsgSender(message.getSender());
        this.baseMapper.baseInsert(messageEntity);
    }

    @Override
    public Integer getUnReadTotal(Integer userId) {
        QueryWrapper<MessageEntity> messageEntityQueryWrapper = new QueryWrapper<>();
        messageEntityQueryWrapper.eq("msg_is_read",MessageEntity.NOTREAD);
        messageEntityQueryWrapper.and(wapper->{
            wapper.eq("msg_recipient",userId);
        });
        List<MessageEntity> messageEntities = this.baseMapper.selectList(messageEntityQueryWrapper);
        return messageEntities.size();
    }

    @Override
    public boolean baseSave(MessageEntity messageEntity) {
        return 0 != this.baseMapper.baseInsert(messageEntity);
    }

    @Override
    public void remindMedication(String timePeriod) {
        Date date = new Date();
        List<UserEntity> userlist = userService.list();
        List<Integer> userIds = userlist.stream().map(item -> {
            return item.getUId();
        }).collect(Collectors.toList());
        userIds.forEach(item->{
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMsgSender(Constant.SENDER_APP);
            messageEntity.setMsgRecipient(item);
            messageEntity.setMsgDateTime(date);
            messageEntity.setMsgIsRead(Constant.MSG_NOT_READ);
            messageEntity.setMsgContent(timePeriod);
            Integer i = this.baseMapper.baseInsert(messageEntity);
        });
    }

    @Override
    public MessageList listDetails(Integer userId) {
        QueryWrapper<MessageEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("msg_recipient",userId);
        List<MessageEntity> messageEntities = this.baseMapper.selectList(wrapper);

        Integer unReadTotal = 0;
        for(MessageEntity item:messageEntities){
            if(item.getMsgIsRead() == Constant.MSG_NOT_READ){
                unReadTotal++;
            }
        }

        List<MessageVo> messageVos = messageEntities.stream().map(item -> {
            SenderEntity byId = senderService.getById(item.getMsgSender());
            String sender = Constant.SENDER_DEFAULT;
            if (Objects.nonNull(byId)) {
                sender = byId.getSedName();
            }

            MessageVo messageVo = new MessageVo();
            messageVo.setId(item.getMsgId());
            messageVo.setContent(item.getMsgContent());
            messageVo.setDate(DateTimeUtil.splitDateNotYear(item.getMsgDateTime(),Constant.SLASH));
            messageVo.setTime(DateTimeUtil.splitTimeNotSecond(item.getMsgDateTime(),Constant.COLON));
            messageVo.setSender(sender);
            messageVo.setIsRead(item.getMsgIsRead());

            return messageVo;
        }).collect(Collectors.toList());

        MessageList messageVo = new MessageList();
        messageVo.setMessages(messageVos);
        messageVo.setTotal(messageEntities.size());
        messageVo.setUnreadTotal(unReadTotal);

        return messageVo;
    }

    @Override
    public R getListByUserId(Integer userId, Integer page) {
        page = (page-1)*7;
        List<MessageVo> res  = messageDao.selectListByUserId(userId,page);
        return R.ok(Map.of("list",res));
    }

}