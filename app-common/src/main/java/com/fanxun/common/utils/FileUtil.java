package com.fanxun.common.utils;

import com.aliyun.oss.OSSClient;

import java.io.ByteArrayInputStream;

/**
 * @Author liu
 * @Date 2018-10-28 21:33
 */
public class FileUtil {
    public static void main(String[] args) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI00HUMIbej0Sn";
        String accessKeySecret = "NiA0RH25ZyL0boRcESfGj6X4TJqrzD ";
        String bucketName = "jianchu";
        String objectName = "test";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        String content = "Hello OSS";
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
