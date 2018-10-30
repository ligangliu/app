package com.fanxun.message.controller;

/**
 * @Author liu
 * @Date 2018-10-23 9:56
 */

import com.fanxun.common.pojo.FanXunResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@CrossOrigin
@RequestMapping("error")
public class ErrorController {
    //private static final String BASE_DIR = "error/";

    @RequestMapping("400")
    @ResponseBody
    public FanXunResult return400(HttpServletRequest request){
        System.out.println("==================400================");
        FanXunResult result = FanXunResult.build(3000,"参数解析异常");
        return result;
    }
    @RequestMapping("404")
    @ResponseBody
    public FanXunResult return404(HttpServletRequest request){
        System.out.println("==================404================");
        FanXunResult result = FanXunResult.build(3000,"404，该请求路径不存在");
        return result;
    }
    @RequestMapping("405")
    @ResponseBody
    public FanXunResult return405(HttpServletRequest request){
        System.out.println("==================404================");
        FanXunResult result = FanXunResult.build(3000,"405，该请求提交方法出错");
        return result;
    }
    @RequestMapping("500")
    @ResponseBody
    public FanXunResult return500(HttpServletRequest request){
        System.out.println("==================500================");
        FanXunResult result = FanXunResult.build(3000,"500错误");
        return result;
    }
}

