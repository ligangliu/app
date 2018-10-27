package com.fanxun.sso.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanxun.common.utils.JsonUtil;
import com.fanxun.common.utils.ParsePostParamsUtil;
import com.fanxun.pojo.TbUser;
import com.github.pagehelper.util.StringUtil;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
    public String test(HttpServletRequest request, TbUser user, String msg1, String msg2) throws Exception{
        System.out.println("===================test============");
        String enctype = request.getContentType();
        System.out.println(enctype);
        if ("GET".equalsIgnoreCase(request.getMethod())){
            System.out.println("GET");
        }else{
            System.out.println("POST");
        }
        if (enctype!=null && enctype.contains("application/json")){
            System.out.println("==================");
            Map<String,String> allParams = ParsePostParamsUtil.getJsonPostParams(request);
            msg1 = allParams.get("msg1");
           // System.out.println(msg1);
            msg2 = allParams.get("xxx");
           // System.out.println(msg2);
        }
        System.out.println(msg1);
        System.out.println(msg2);

        return "xxxxxxx";
    }


}
