package com.fanxun.message.service.impl;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.DateUtil;
import com.fanxun.common.utils.ExceptionUtil;
import com.fanxun.common.utils.UUIDUtil;
import com.fanxun.mapper.TbMessageMapper;
import com.fanxun.message.dao.JedisClient;
import com.fanxun.message.service.MessageService;
import com.fanxun.pojo.TbMessage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liu
 * @Date 2018-10-29 16:58
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private TbMessageMapper messageMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_MESSAGE_IS_READ}")
    private String REDIS_MESSAGE_IS_READ;

    /**
     * 插入一个发布为所有用户的消息
     * @param message
     * @return
     */
    @Override
    public FanXunResult insertMessageAllUsers(TbMessage message) {
        if (message == null){
            return FanXunResult.build(3000,"插入消息不能为空");
        }
        try{
            //将订阅用户设置为0,避免其为null
            message.setSubscribeUserid("0");
            message.setMessageUuid(UUIDUtil.getUUID());
            String messageAddTime = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
            message.setMessageAddTime(messageAddTime);
            int result = messageMapper.insert(message);
            if (result > 0){
                String key = REDIS_MESSAGE_IS_READ+ ":" + message.getMessageUuid();
                jedisClient.sadd(key,"0");
            }
            return FanXunResult.build(1000,"所有用户订阅消息插入成功");
        }catch (Exception e){
            return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 批量插入数据
     * @param message
     * @param subscribeUserIds
     * @return
     */
    @Override
    public FanXunResult insertMessageSubscribes(TbMessage message, List<String> subscribeUserIds) {
        if (message == null){
            return FanXunResult.build(3000,"插入消息不能为空");
        }
        if (subscribeUserIds == null || subscribeUserIds.size() == 0){
            return FanXunResult.build(3000,"插入用户id不能为空");
        }
        try {
            String messageUuid = UUIDUtil.getUUID();
            message.setMessageUuid(messageUuid);
            String messageAddTime = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
            message.setMessageAddTime(messageAddTime);
            int result = messageMapper.insertBatch(message,subscribeUserIds);
            //添加至redis中，维护用户是否读取列表
            String key = REDIS_MESSAGE_IS_READ + ":" + messageUuid;
            jedisClient.sadd(key,"0");
            return FanXunResult.build(1000,"指定用户消息插入成功");
        }catch (Exception e){
            return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 通过用户id与消息类型(默认为所有)获取用户通知消息
     * @param subscribeUserId
     * @param messageType
     * @param page
     * @param row
     * @return
     */
    @Override
    public FanXunResult getAllMessagesByUserId(String subscribeUserId, String messageType,Integer page, Integer row) {
        PageHelper.startPage(page,row);
        List<TbMessage> allMessages = messageMapper.selectBySubscribeUserId(subscribeUserId,messageType);
        for (TbMessage message:allMessages){
            long isRead = jedisClient.sismenber(REDIS_MESSAGE_IS_READ+":" +
                    message.getMessageUuid(),subscribeUserId);
           // System.out.println("isRead: " + isRead);
            message.setIsRead((int)isRead);
        }
        // System.out.println("size: " + allMessages.size());
        PageInfo<TbMessage> pageInfo = new PageInfo<>(allMessages);
        return FanXunResult.build(1000,"OK", pageInfo);
    }

    /**
     * 根据用户的id以及消息的唯一标志符将已经读取的消息redis插入至redis中
     * @param subscribeUserId :订阅用户的id
     * @param messageUuids ：已读取的消息标志
     * @return
     */
    @Override
    public FanXunResult updateReadStatus(String subscribeUserId, List<String> messageUuids) {
        String key = null;
        for (String messageUuid:messageUuids){
            key = REDIS_MESSAGE_IS_READ + ":" + messageUuid;
            try {
                jedisClient.sadd(key,subscribeUserId);
            } catch (Exception e){
                return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
            }
        }
        return FanXunResult.build(1000,"已读取消息列表更新成功");
    }

}
