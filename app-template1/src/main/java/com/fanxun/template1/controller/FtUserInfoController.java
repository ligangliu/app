package com.fanxun.template1.controller;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.pojo.TbFtUserinfo;
import com.fanxun.template1.service.FtUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author liu
 * @Date 2018-11-13 15:29
 */
@Controller
@RequestMapping("/api/v1/ft")
public class FtUserInfoController {

    @Autowired
    private FtUserInfoService userInfoService;

    @RequestMapping(value = "/insertUserinfo",method = RequestMethod.POST)
    @ResponseBody
    public FanXunResult createFtUserInfo(@RequestBody TbFtUserinfo userinfo){
        if (null == userinfo){
            return FanXunResult.build(3000,"参数不能为空");
        }
        if (userinfo.getCid() == null){
            return FanXunResult.build(3000,"文章id不能为空");
        }
        if (StringUtils.isEmpty(userinfo.getUsername())){
            return FanXunResult.build(3000,"报名用户名不能为空");
        }
        if (StringUtils.isEmpty(userinfo.getPhone())){
            return FanXunResult.build(3000,"报名用户电话不能为空");
        }
        return userInfoService.createFtUserInfo(userinfo);
    }

    @RequestMapping(value = "/getAllUsers/{cid}",method = RequestMethod.GET)
    @ResponseBody
    public FanXunResult getAllFtUserInfo(@PathVariable Integer cid,@RequestParam(required = false,
            defaultValue = "1",value = "page")Integer page, @RequestParam(
            required = false,defaultValue = "10",value = "row")Integer row){
        System.out.println("================getAllUsersInfo======================");
        return userInfoService.getAllFtUserInfo(cid,page,row);
    }


}
