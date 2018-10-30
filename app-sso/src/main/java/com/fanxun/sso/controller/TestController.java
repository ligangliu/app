package com.fanxun.sso.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanxun.common.pojo.FanXunResult;
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
    public String test(HttpServletRequest request,@RequestBody String json){
        System.out.println("===================test============");
        JSONObject jsonObject = JSON.parseObject(json);
        Set<Map.Entry<String,Object>> set = jsonObject.entrySet();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JSONArray) {
                System.out.println(key + " --- " + JSON.toJSONString(value));
            } else {
                System.out.println(key + " --- " + String.valueOf(value));
            }
        }
        return "xxxxxxx";
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1(HttpServletRequest request,@RequestBody TbUser user){
        System.out.println("===================test============");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return "xxxx";
    }


}
