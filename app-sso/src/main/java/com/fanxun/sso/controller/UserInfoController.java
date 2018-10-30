package com.fanxun.sso.controller;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.ExceptionUtil;
import com.fanxun.common.utils.ParsePostParamsUtil;
import com.fanxun.pojo.TbUserinfo;
import com.fanxun.sso.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @Author liu
 * @Date 2018-10-27 20:23
 */

@Controller
@CrossOrigin
@RequestMapping("/api/v1")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value="/collectInfo")
    @ResponseBody
    public FanXunResult createUserInfo(HttpServletRequest request, TbUserinfo userinfo){
        System.out.println("====================createUserInfo==========================");
        if ("POST".equalsIgnoreCase(request.getMethod())){
            String enctype = request.getContentType();
            if (enctype != null && enctype.contains("application/json")){
                Map<String,String> allParams = null;
                try {
                    allParams = ParsePostParamsUtil.getJsonPostParams(request);
                }catch (IOException e){
                    return FanXunResult.build(3000,"参数转换异常");
                }
                userinfo.setPhone(allParams.get("phone"));
                userinfo.setName(allParams.get("name"));
            }
        }
        if (userinfo.getPhone() == null || userinfo.getPhone().equals("")){
            return FanXunResult.build(3000,"手机号不能为空");
        }
        if (userinfo.getName() == null || userinfo.getName().equals("")){
            return FanXunResult.build(3000,"用户名不能为空");
        }
        if (userinfo.getStatus() != null ){
            if (userinfo.getStatus() != 1 || userinfo.getStatus() != 2 || userinfo.getStatus() != 3){
                return FanXunResult.build(3000,"status只能为1,2,3");
            }
        }
        try {
            FanXunResult result = userInfoService.createUserInfo(userinfo);
            return result;
        } catch (Exception e) {
            return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
        }
    }

}
