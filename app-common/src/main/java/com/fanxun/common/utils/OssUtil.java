package com.fanxun.common.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liu
 * @Date 2018-11-02 10:06
 */
public class OssUtil {

    private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";

    private static String accessKeyId = "LTAIr2sb5pZkdAJc";

    private static String accessKeySecret = "pLXJP06lfmZlumIBrSf8gRwagcoNn2";

    private static String bucketName = "jianchuapp";

    private static OSSClient getOSSClient() {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(5000);
        conf.setMaxErrorRetry(10);
        return new OSSClient(endpoint, accessKeyId, accessKeySecret,conf);
    }

    /**
     * 判断bucket是否存在
     * @param bucketName
     * @return
     */
    public static boolean isExistsBucket(String bucketName){
        OSSClient client = null;
        client = getOSSClient();
        if (null == client){
            System.out.println("oss对象连接失败");
            return false;
        }
        client.shutdown();
        return client.doesBucketExist(bucketName);
    }

    public boolean isExistsObject(String objectName){
        OSSClient client = null;
        client = getOSSClient();
        if (null == client){
            System.out.println("oss对象连接失败");
            return false;
        }
        client.shutdown();
        return client.doesObjectExist(bucketName,objectName);
    }

    /**
     * 创建bucket
     * @param bucketName
     * @return
     */
    public static boolean createBucket(String bucketName){
        OSSClient client = null;
        try {
            client = getOSSClient();
            if (null == client){
                System.out.println("oss对象连接失败");
                return false;
            }
            if (client.doesBucketExist(bucketName)){
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                client.createBucket(bucketName);
            }
            return true;
        }catch (OSSException oe) {
            System.out.println("创建bucket信息失败");
            return false;
        } finally {
            client.shutdown();
        }
    }

    /**
     *查看bucket信息
     * @param bucketName
     * @return
     */
    public static BucketInfo getBucketInfo(String bucketName){
        OSSClient client = null;
        try {
            client = getOSSClient();
            if (null == client){
                System.out.println("oss对象连接失败");
                return null;
            }
            if (!client.doesBucketExist(bucketName)){
                System.out.println("该" +  bucketName + "不存在");
                return null;
            }
            BucketInfo info = client.getBucketInfo(bucketName);
            //System.out.println(info.getBucket());
            return info;
        }catch (OSSException oe) {
            System.out.println("读取bucket信息失败");
            return null;
        } finally {
            client.shutdown();
        }
    }

    /**
     * 获取bucket中所有keys
     * @return
     */
    public static List<String> getBucketKeys(String bucketName){
        OSSClient client = null;
        try {
            client = getOSSClient();
            if (null == client){
                System.out.println("oss对象连接失败");
                return null;
            }
            List<String> result = new ArrayList<>();
            ObjectListing objectListing = client.listObjects(bucketName);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
           // System.out.println("您有以下Object：");
            for (OSSObjectSummary object : objectSummary) {
                result.add(object.getKey());
                //System.out.println("\t" + object.getKey());
            }
            return result;
        } catch (OSSException oe) {
            return null;
        } finally {
            client.shutdown();
        }
    }

    /**
     * 上传内容到指定的存储空间(bucketName)，并保存为指定的文件名称(objectName)
     * @param objectName
     * @param content
     * @return
     */
    public static boolean putContent(String contentName,String content){
        OSSClient client = null;
        try {
            client = getOSSClient();
            if (null == client){
                System.out.println("oss对象连接失败");
                return false;
            }
            client.putObject(bucketName,contentName,new ByteArrayInputStream(content.getBytes()));
            return true;
        } catch (OSSException oe) {
            return false;
        } finally {
            client.shutdown();
        }
    }

    /**
     * 上传文件,存在会覆盖
     * @param fileName
     * @param filePath
     * @return
     */
    public static boolean putFile(String fileName,String filePath){
        OSSClient client = null;
        try {
            client = getOSSClient();
            if (null == client){
                System.out.println("oss对象连接失败");
                return false;
            }
            InputStream inputStream = new FileInputStream(filePath);
            client.putObject(bucketName,fileName,inputStream);
            return true;
        } catch (OSSException oe) {
            return false;
        } catch (Exception e){
            System.out.println("文件路径不存在");
            return false;
        }finally {
            client.shutdown();
        }
    }

    /**
     * 获取文件内容
     * @param contentName
     * @return
     */
    public static String getContent(String contentName){
        OSSClient client = null;
        try {
            client = getOSSClient();
            if (null == client){
                System.out.println("oss对象连接失败");
                System.out.println("获取ossl");
                return null;
            }
            if (!client.doesObjectExist(bucketName,contentName)){
                System.out.println("该contentName不存在");
                return null;
            }
            OSSObject ossObject = client.getObject(bucketName, contentName);
            InputStream inputStream = ossObject.getObjectContent();
            StringBuilder objectContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                objectContent.append(line);
            }
            inputStream.close();
            return objectContent.toString();
        } catch (OSSException oe) {
            System.out.println("读取文件失败");
            return null;
        } catch (Exception e){
            System.out.println("读取文件失败");
            return null;
        } finally {
            client.shutdown();
        }
    }

    /**
     * 把fileName的文件下载至path路径下
     * @param fileName
     * @param path
     * @return
     */
    public static boolean getFile(String fileName,String path){
        OSSClient client = null;
        try {
            client = getOSSClient();
            if (null == client){
                System.out.println("oss对象连接失败");
                System.out.println("获取ossl");
                return false;
            }
            if (!client.doesObjectExist(bucketName,fileName)){
                System.out.println("该fileName不存在");
                return false;
            }
            //下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
            client.getObject(new GetObjectRequest(bucketName,fileName),new File(path+File.separator+fileName));
            return true;
        } catch (OSSException oe) {
            System.out.println("下载文件失败");
            return false;
        } catch (Exception e){
            System.out.println("下载文件失败");
            return false;
        } finally {
            client.shutdown();
        }
    }

    /**
     * 根据objectName删除
     * @param objectName
     * @return
     */
    public static boolean deleteObject(String objectName){
        OSSClient client = null;
        try {
            client = getOSSClient();
            if (null == client){
                System.out.println("oss对象连接失败");
                System.out.println("获取ossl");
                return false;
            }
            if (!client.doesObjectExist(bucketName,objectName)){
                System.out.println("该objectName不存在");
                return false;
            }
            client.deleteObject(bucketName,objectName);
            return true;
        } catch (OSSException oe) {
            System.out.println("删除object失败");
            return false;
        } catch (Exception e){
            System.out.println("读取文件失败");
            return false;
        } finally {
            client.shutdown();
        }
    }

    public static void main(String[] args) {
//        boolean re = OssUtil.putContent("ftcontent/aa","xxxx");
////        System.out.println(re);
////        List<String> keys = OssUtil.getBucketKeys("jianchu");
////        for (String k:keys){
////            System.out.println(k);
////        }
      //  OssUtil.getBucketInfo("jianchuapp");
    }
}
