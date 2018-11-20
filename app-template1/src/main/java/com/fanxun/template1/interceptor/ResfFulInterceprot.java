package com.fanxun.template1.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.JsonUtil;
import com.fanxun.common.utils.ParsePostParamsUtil;
import com.fanxun.pojo.TbUser;
import com.fanxun.template1.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器url
 * @Author liu
 * @Date 2018-11-13 12:09
 */
public class ResfFulInterceprot implements HandlerInterceptor {

    @Autowired
    private TokenDao tokenDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String token = null;
        token = request.getHeader("token");
        if (requestURI.contains("/api/v1/ft/")){
            if (requestURI.endsWith("/insertUserinfo")){
                return true;
            }
            if (requestURI.contains("/getSelected")){
                return true;
            }
            if (token == null){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                Writer writer = response.getWriter();
                writer.write(JsonUtil.objectToJson(FanXunResult.build(3000,"缺少token信息")));
                return false;
            }else{
                TbUser user = tokenDao.getUserByToken(token);
                if (user == null){
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    Writer writer = response.getWriter();
                    writer.write(JsonUtil.objectToJson(FanXunResult.build(3000,"token信息错误或已过期")));
                    return false;
                }else {
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
