package com.fanxun.message.service;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.pojo.TbMessage;

import java.util.List;

/**
 * @Author liu
 * @Date 2018-10-29 16:58
 */
public interface MessageService {
    public FanXunResult insertMessageAllUsers(TbMessage message);
    public FanXunResult insertMessageSubscribes(TbMessage message, List<String> subscribeUserIds);
    public FanXunResult getAllMessagesByUserId(String subscribeUserId, String messageType,Integer page,Integer row);
    public FanXunResult updateReadStatus(String subscribeUserId, List<String> messageUuids);
}
