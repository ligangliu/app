package com.fanxun.sso.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.DateUtil;
import com.fanxun.common.utils.JsonUtil;
import com.fanxun.common.utils.ParsePostParamsUtil;
import com.fanxun.pojo.TbUser;
import com.github.pagehelper.util.StringUtil;
import com.sun.imageio.plugins.common.I18N;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Author liu
 * @Date 2018-10-24 19:24
 */

/**
 * application/x-www-form-urlencoded ,
 * multipart/form-data ，
 * application/json ，
 * text/xml
 */
@Controller
@CrossOrigin
@RequestMapping("/api/v1")
public class TestController {
    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(HttpServletResponse response){
        System.out.println("===================test============");
        String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
        String contentType = response.getHeader("Content-Type");
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        String result = contentType + "  系统时间为： " + date;
        return result;
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public FanXunResult test1(){
        String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
        return FanXunResult.build(1000,"系统时间为：" + date);
    }


    @RequestMapping(value = "/test2")
    @ResponseBody
    public String test2(){
        String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
        return "系统时间为： " + date;
    }

}
