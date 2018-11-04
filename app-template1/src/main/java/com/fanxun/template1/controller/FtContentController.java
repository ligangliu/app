package com.fanxun.template1.controller;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.OssWebUtil;
import com.fanxun.pojo.TbFtContent;
import com.fanxun.template1.service.FtContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author liu
 * @Date 2018-11-02 14:38
 */
@Controller
@CrossOrigin
@RequestMapping("/api/v1")
public class FtContentController {

    @Autowired
    private FtContentService contentService;

    @RequestMapping(value = "getSignature",method = RequestMethod.GET)
    @ResponseBody
    public FanXunResult getSignature(){
        System.out.println("==================getSignature=======================");
        Map<String,String> map = OssWebUtil.getWebParams();
        return FanXunResult.build(1000,"ok",map);
    }

    /**
     * 插入一个文章
     * @param content
     * @return
     */
    @RequestMapping(value = "insertContent",method = RequestMethod.POST)
    @ResponseBody
    public FanXunResult insertContent(@RequestBody TbFtContent content){
        System.out.println("==================insertContent===========================");
        if (null == content){
            return FanXunResult.build(3000,"参数不能为空");
        }
        if (StringUtils.isEmpty(content.getTitle())){
            return FanXunResult.build(3000,"文章标题不能为空");
        }
        if (StringUtils.isEmpty(content.getContent())){
            return FanXunResult.build(3000,"文章内容不能为空");
        }
        if (null == content.getUid()){
            return FanXunResult.build(3000,"用户id不能为空");
        }
        if (null == content.getAppid()){
            return FanXunResult.build(3000,"小程序id不能为空");
        }
        return contentService.insertContent(content);
    }

    /**
     * 根据小程序id,用户id,分页获取其拥有的所有文章用户前端展示，
     * @param appId
     * @param uid
     * @param page
     * @param row
     * @return
     */
    @RequestMapping(value = "/getAllContent/{appId}/{uid}" ,method = RequestMethod.GET)
    @ResponseBody
    public FanXunResult getAllContentsByAppid(@PathVariable Integer appId, @PathVariable Integer uid,
                                              @RequestParam(required = false,
                                                      defaultValue = "1",value = "page")Integer page, @RequestParam(
            required = false,defaultValue = "5",value = "row")Integer row){
        System.out.println("==================getAllContentsByAppid=========================");
        if (appId == null){
            return FanXunResult.build(3000,"小程序id不能为空");
        }
        if (uid == null){
            return FanXunResult.build(3000,"用户id不能为空");
        }
        return contentService.getAllContentsByAppid(appId,uid,page,row);
    }

    /**
     * 更新文章的内容
     * @param content
     * @return
     */
    @RequestMapping(value = "/updateContent",method = RequestMethod.POST)
    @ResponseBody
    public FanXunResult updateContent(@RequestBody TbFtContent content){
        System.out.println("====================updateContent=========================");
        if (null == content){
            return FanXunResult.build(3000,"参数不能为空");
        }
        if (null == content.getCid()){
            return FanXunResult.build(3000,"文章id不能为空");
        }
        if (null == content.getUid()){
            return FanXunResult.build(3000,"用户id不能为空");
        }
        if (null == content.getAppid()){
            return FanXunResult.build(3000,"小程序id不能为空");
        }
        return contentService.updateContent(content);
    }

    /**
     * 根据小程序id,用户id,文章id,去将该文章的isSelected字段设置为1
     * 并将上次的cid文章的isSelected字段设置为0
     * @param appId
     * @param uid
     * @param cid
     * @param lastSelectedCid
     * @return
     */
    @RequestMapping(value = "/updateSelected/{appId}/{uid}/{cid}",
            method = RequestMethod.GET)
    @ResponseBody
    public FanXunResult updateContentSelectd(@PathVariable Integer appId,@PathVariable Integer uid,
                                             @PathVariable Integer cid,Integer lastSelectedCid){
        if (appId == null){
            return FanXunResult.build(3000,"小程序id不能为空");
        }
        if (uid == null){
            return FanXunResult.build(3000,"用户id不能为空");
        }
        if (cid == null){
            return FanXunResult.build(3000,"文章id不能为空");
        }
        return contentService.updateContentSelectd(appId,uid,cid,lastSelectedCid);
    }

    /**
     * 根据小程序id,用户id去数据库中加载用户已经选择的文章内容
     * @param appId
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getSelected/{appId}/{uid}",method = RequestMethod.GET)
    public FanXunResult getContentSelected(@PathVariable Integer appId,@PathVariable Integer uid){
        if (appId == null){
            return FanXunResult.build(3000,"小程序id不能为空");
        }
        if (uid == null){
            return FanXunResult.build(3000,"用户id不能为空");
        }
        return contentService.getContentSelectd(appId,uid);
    }

}
