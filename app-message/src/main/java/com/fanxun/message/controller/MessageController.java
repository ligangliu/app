package com.fanxun.message.controller;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.message.service.MessageService;
import com.fanxun.pojo.TbMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author liu
 * @Date 2018-10-29 19:48
 */
@Controller
@CrossOrigin
@RequestMapping("/api/v1")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 根据用户id,消息类型(默认为全部)获取改用户的所有消息
     * @param subscribeUserId ：用户id
     * @param messageType：消息类型
     * @param page：查询页，默认为1
     * @param row：每页显示消息条数，默认为5
     * @return
     */
    @RequestMapping(value = "/getMessages/{subscribeUserId}" ,method = RequestMethod.GET)
    @ResponseBody
    public FanXunResult getAllMessagesBySubscribeUserId
            (@PathVariable String subscribeUserId, @RequestParam(required = false,
                    defaultValue = "0",value = "messageType")String messageType,
             @RequestParam(required = false,
                    defaultValue = "1",value = "page")Integer page, @RequestParam(
                            required = false,defaultValue = "5",value = "row")Integer row
             ){
        System.out.println("=============getAllMessagesBySubscribeUserId==============");
        if (subscribeUserId == null){
            return FanXunResult.build(3000,"用户id不能为空");
        }
        System.out.println("subscribeUserId: " + subscribeUserId);
        System.out.println("page: " + page);
        System.out.println("row: " + row);
        System.out.println("messageType: " + messageType);
        FanXunResult result = messageService.getAllMessagesByUserId(subscribeUserId,messageType,page,row);
        return result;
    }

    /**
     * 管理后台利用改接口插入消息，可分为插入所有订阅者消息，也可批量插入指定用户消息
     * 当插入所有订阅者消息时，将isAll传入为1
     * 当插入指定用户消息时，将isAll传入为0并i企鹅将订阅用户的subscribeUserid传入为1,2,3,4,5格式
     * @param message
     * @return
     */
    @RequestMapping(value = "/insertMessage",method = RequestMethod.POST)
    @ResponseBody
    public FanXunResult insertMessage(@RequestBody TbMessage message){
        System.out.println("=============insertMessage==============");
        if (message == null){
            return FanXunResult.build(3000,"插入消息不能为空");
        }
        if (message.getMessageAddUserid() == null){
            return FanXunResult.build(3000,"消息添加者id不能为空");
        }
        if (message.getMessageType() == null){
            return FanXunResult.build(3000,"消息类型不能为空");
        }
        if (StringUtils.isEmpty(message.getMessageThema())){
            return FanXunResult.build(3000,"消息主题不能为空");
        }
        if (StringUtils.isEmpty(message.getMessageContent())){
            return FanXunResult.build(3000,"消息内容不能为空");
        }
        if (message.getIsAll() == null){
            return FanXunResult.build(3000,"isAll属性不能为空");
        }
        if (message.getIsAll() != 0 && message.getIsAll() != 1){
            return FanXunResult.build(3000, "isAll只能为数字1或2");
        }

        //表示插入的该消息为所有用户应该订阅的消息
        if (message.getIsAll() == 1){
            if (!StringUtils.isEmpty(message.getSubscribeUserid())){
                return FanXunResult.build(3000,"此消息为全部用户订阅消息,消息订阅用户id必须为空");
            }
            return messageService.insertMessageAllUsers(message);
        }
        //批量插入特定用户消息
        if (message.getIsAll() == 0 && StringUtils.isEmpty(message.getSubscribeUserid())) {
            return FanXunResult.build(3000,"此消息为非全部用户订阅消息,消息订阅用户id不能为空");
        }
        ArrayList<String> subscribeUserIds = new ArrayList<>
                (Arrays.asList(message.getSubscribeUserid().split(",")));
        return messageService.insertMessageSubscribes(message,subscribeUserIds);
    }

    /**
     * 通过用户的id以及消息唯一标识符更新用户消息是否已读列表
     * @param subscribeUserId：订阅用户信息id
     * @param messageUuids：消息唯一标识符列表
     * @return
     */
    @RequestMapping(value = "/updateReadStatus" ,method = RequestMethod.GET)
    @ResponseBody
        public FanXunResult updateReadStatus(String subscribeUserId, @RequestParam("messageUuids")
            List<String> messageUuids){
        System.out.println("========================updateReadStatus========================");
        System.out.println("subscribeUserId: " + subscribeUserId);
        System.out.println("messageUuids[0]: " + messageUuids.get(0));
        if (StringUtils.isEmpty(subscribeUserId)){
            return FanXunResult.build(3000,"用户id不能为空");
        }
        if (messageUuids == null || messageUuids.size() == 0){
            return FanXunResult.build(3000,"消息标识符不能为空");
        }
        return messageService.updateReadStatus(subscribeUserId, messageUuids);
    }

}
