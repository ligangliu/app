package com.fanxun.common.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author liu
 * @Date 2018-11-02 14:20
 */
public class OssWebUtil {
    private static String accessId = "LTAI00pk3hYFrtKz";
    private static String accessKey = "IrakJslpEvNCD2qzIP3BUNfUihcMa4";
    private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    private static String bucket = "jianchu";                    // 请填写您的 bucketname 。
    private static String host = "http://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint

    // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
    private static String callbackUrl;
    private static String dir = "content"; // 用户上传文件时指定的前缀。

    public static Map<String,String> getWebParams(){
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new HashMap<>();
            respMap.put("OSSAccessKeyId", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            respMap.put("success_action_status","200");
            return respMap;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

//    public static void main(String[] args) {
//        Map<String,String> result = OssWebUtil.getWebParams();
//        for (Map.Entry<String,String> entry : result.entrySet()){
//            System.out.println(entry.getKey() + "---" + entry.getValue());
//        }
//    }
}
