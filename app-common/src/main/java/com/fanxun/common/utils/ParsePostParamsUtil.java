package com.fanxun.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author liu
 * @Date 2018-10-27 10:14
 */
public class ParsePostParamsUtil {

    /**
     * 处理application/x-www-form-urlencoded
     * 普通表单处理的方式
     * springmvc可直接与其绑定
     * request.getParameterMap();也可以获取参数
     *
     */
    public Map<String,String[]> getParamFromRequest(HttpServletRequest request){
        return request.getParameterMap();
    }
    /**
     * multipart/form-data
     */
    /**
     * 处理application/json格式
     * @param postString
     * @return
     */
    public static Map<String,String> getJsonPostParams(HttpServletRequest request) throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = Charset.defaultCharset().name();
        }
        return getPostParams(new String(buffer, charEncoding));
    }
    private static String getJSONString(HttpServletRequest request) throws Exception{
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = Charset.defaultCharset().name();
        }
        return new String(buffer, charEncoding);
    }
    private static Map<String,String> getPostParams(String postString){
        //postString = JsonUtil.objectToJson(postString);
//        System.out.println("=================================");
//        System.out.println(postString);
//        System.out.println("==================================");
        Map<String,String> allParams = new HashMap<>();
        if (!StringUtils.isEmpty(postString)){
            JSONObject jsonObject = JSON.parseObject(postString);
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof JSONArray) {
                    allParams.put(key, JSON.toJSONString(value));
                } else {
                    allParams.put(key, String.valueOf(value));
                }
            }
        }
        return allParams;
    }

    /**
     * 获取post请求中得所有参数，并且返回该参数得一个字符串
     *
     * @param request
     * @return
     * @throws IOException
     */

    private static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        /*当无请求参数时，request.getContentLength()返回-1 */
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }

        return buffer;
    }
}
