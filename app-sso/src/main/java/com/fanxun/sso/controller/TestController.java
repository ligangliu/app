package com.fanxun.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author liu
 * @Date 2018-10-24 19:24
 */
@Controller
@RequestMapping("/api/v1")
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "xxxxxxxxxxxxxxxxxxx";
    }

}
