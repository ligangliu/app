package com.fanxun.common.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.List;

/**
 * @Author liu
 * @Date 2018-11-04 20:39
 */
public class ZkUtil {

    private final static String connectString = "192.168.230.200:2181,192.168.230.201:2181,192.168.230.202:2181";

    private final static String namespace = "";

    private static int baseSleepTimeMs = 1000;

    private static int maxRetries = 3;

    private static int sessionTimeoutMs = 10000;
    /**
     * -------------------------------------创建节点----------------------------------
     * 1.创建一个空节点，初始内容为空
     *      client.create().forPath("path");
     * 2.创建一个节点，附带初始化内容
     *      client.create().forPath("path","init".getBytes());
     * 3.创建一个节点，指定创建模式(临时节点)，内容为空
     *     client.create().withMode(CreateMode.EPHEMERAL).forPath("path");
     * 4.递归创建父节点
     *      client.creatingParentContainersIfNeeded()
     * 以上多个流式接口时可以自由组合使用
     * ------------------------------------------------------------------------------
     *
     * -------------------------------------删除节点----------------------------------
     * 1.删除一个节点
     *      client.delete.forPath("path")
     * 2.删除一个节点，并递归删除其所有子节点
     *      client.delete().deletingChildrenIfNeeded().forPath("path");
     * 3.删除一个节点，强制指定版本进行删除
     *      client.delete().withVersion(10086).forPath("path");
     * 4.删除一个节点，强制保证删除
     *      client.delete().guaranteed().forPath("path");
     *      guaranteed()接口是一个保障措施，只要客户端会话有效，
     *      那么Curator会在后台持续进行删除操作，直到删除节点成功。
     *
     * -------------------------------------读取数据----------------------------------
     * 1.读取一个节点的数据内容
     *     client.getData().forPath("path");  返回的时byte[]
     * 2.读取一个节点的数据内容，同时获取到该节点的stat
     *      Stat stat = new Stat();
     *      client.getData().storingStatIn(stat).forPath("path");
     *
     * --------------------------------------更新节点数据------------------------------
     * 1.更新一个节点的数据内容
     *      client.setData().forPath("path","data".getBytes());   返回一个Stat实例
     * 2.更新一个节点的数据内容，强制指定版本进行更新
     *      client.setData().withVersion(10086).forPath("path","data".getBytes());
     *
     * --------------------------------------其他---------------------------------
     * 1.检查节点是否存在
     *      client.checkExists().forPath("path");该方法返回一个Stat实例
     * 2.获取某个节点的所有子节点路径
     *      client.getChildren().forPath("path"); 方法的返回值为List<String>
     * -------------------------------------------------------------------------------
     *
     */
    private static CuratorFramework getClient(){
        CuratorFramework client = null;
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs,maxRetries);
        client = CuratorFrameworkFactory.builder().connectString
                (connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy).namespace(namespace).build();
        client.start();
        return client;
    }

    /**
     * 递归创建持久节点
     * @param path
     * @param bytes
     * @throws Exception
     */
    public static boolean createNodePersistent(String path,byte[] bytes) throws Exception {
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return false;
        }
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path,bytes);
            if (null != client){
                client.close();
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 递归创建临时节点
     * @param path
     * @param bytes
     * @throws Exception
     */
    public static boolean createNodeEphemeral(String path,byte[] bytes) throws Exception {
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return false;
        }
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path,bytes);
            Thread.sleep(2000);
            if (null != client){
                client.close();
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    /**
     * 递归删除删除所有节点及其子节点
     *
     * @param path
     * @throws Exception
     */
    public static boolean deleteNode(String path) throws Exception {
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return false;
        }
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
            if (null != client){
                client.close();
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 读取某个节点的数据
     * @param path
     * @return
     * @throws Exception
     */
    public static byte[] getData(String path) throws Exception {
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return null;
        }
        try {
            byte[] result = client.getData().forPath(path);
            if (null != client){
                client.close();
            }
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean setData(String path,byte[] data) throws Exception{
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return false;
        }
        try {
            client.setData().forPath(path,data);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 检查某个节点是否存在
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean isExists(String path) throws Exception {
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return false;
        }
        try {
            if (null == client.checkExists().forPath(path)){
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 获取某个节点的所有子节点路径
     * @param path
     * @return
     */
    public static List<String> ls(String path){
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return null;
        }
        try {
            return client.getChildren().forPath(path);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 对path这个节点内容的变化进行监听
     * @param
     * @throws Exception
     */
    public static boolean addNodeDataWatcher(String path) throws Exception {
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return false;
        }
        final NodeCache nodeCache = new NodeCache(client,path);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                String data = new String(nodeCache.getCurrentData().getData());
                System.out.println("path= "+nodeCache.getCurrentData().getPath()+"数据更新 : data= " +data);
            }
        });
        return true;
    }

    /**
     * 对某个节点的子节点进行建通
     * @param path
     * @throws Exception
     */
    public static boolean addChildWatcher(String path) throws Exception {
        CuratorFramework client = getClient();
        if (null == client){
            System.out.println("zk连接获取失败");
            return false;
        }
        final PathChildrenCache childrenCache = new PathChildrenCache(client,path,true);
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        System.out.println(childrenCache.getCurrentData());
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {
                    System.out.println("客户端子节点cache初始化数据完成");
                }else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)){
                    System.out.println("添加子节点：" + event.getData().getPath());
                }else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)){
                    System.out.println("删除字节点：" + event.getData().getPath());
                }else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)){
                    System.out.println("修改子节点数据：" + event.getData().getPath());
                }
            }
        });
        return true;
    }
//    public static void main(String[] args) throws Exception {
//        ZkUtil.addNodeDataWatcher("/base/curator/test");
//        ZkUtil.addChildWatcher("/base/curator");
//        Thread.sleep(1000000);
//        System.out.println("================");
//    }

}
