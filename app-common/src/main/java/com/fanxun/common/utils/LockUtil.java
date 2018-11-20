package com.fanxun.common.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 用于跨系统或者跨服务器之间的锁，不能用于同一个虚拟机
 * @author Administrator
 *
 */
public class LockUtil {
    private static CuratorFramework client = null;
    protected static CountDownLatch latch = new CountDownLatch(1);
    protected static CountDownLatch shardLocklatch = new CountDownLatch(1);
    private static String selfIdentity=null;
    private static String selfNodeName= null;
    public static synchronized void init(String connectString) {
        if (client != null)
            return;

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder().connectString(connectString)
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy)
                .namespace("LockService").build();
        client.start();

        // 创建锁目录
        try {
            if (client.checkExists().forPath("/ExclusiveLockDemo") == null) {
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(Ids.OPEN_ACL_UNSAFE)
                        .forPath("/ExclusiveLockDemo");
            }
            // 创建锁监听
            addChildWatcher("/ExclusiveLockDemo");
            if (client.checkExists().forPath("/ShardLockDemo") == null) {
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(Ids.OPEN_ACL_UNSAFE).forPath("/ShardLockDemo");
            }
        } catch (Exception e) {
            throw new RuntimeException("ZK服务器连接不上");
        }
    }

    public static synchronized void getExclusiveLock() {
        while (true) {
            try {
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(Ids.OPEN_ACL_UNSAFE)
                        .forPath("/ExclusiveLockDemo/lock");
                return;// 如果节点创建成功，即说明获取锁成功
            } catch (Exception e) {
                try {
                    //如果没有获取到锁，需要重新设置同步资源值
                    if(latch.getCount()<=0){
                        latch = new CountDownLatch(1);
                    }
                    latch.await();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param type
     *            0为读锁，1为写锁
     * @param identity
     *            获取当前锁的所有者
     */
    public static boolean getShardLock(int type, String identity) {
        if (identity == null || "".equals(identity)) {
            throw new RuntimeException("identity不能为空");
        }
        if (identity.indexOf("-") != -1) {
            throw new RuntimeException("identity不能包含字符-");
        }
        if (type != 0 && type != 1) {
            throw new RuntimeException("type只能为0或者1");
        }
        String nodeName = null;
        if (type == 0) {
            nodeName = "R" + identity + "-";
        } else if (type == 1) {
            nodeName = "W" + identity + "-";
        }
        selfIdentity = nodeName;
        try {
            //if (client.checkExists().forPath("/ShardLockDemo/" + nodeName) == null)
            selfNodeName = client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .withACL(Ids.OPEN_ACL_UNSAFE)
                    .forPath("/ShardLockDemo/" + nodeName);
            List<String> lockChildrens = client.getChildren().forPath(
                    "/ShardLockDemo");
            if (!canGetLock(lockChildrens, type,
                    nodeName.substring(0, nodeName.length() - 1),false)) {
                shardLocklatch.await();
            }
            // return;// 获得锁成功就返回
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static boolean canGetLock(List<String> childrens, int type,
                                      String identity,boolean reps) {
        boolean res = false;
        if(childrens.size()<=0)
            return true;

        try {
            String currentSeq = null;
            List<String> seqs = new ArrayList<String>();
            //List<String> identitys = new ArrayList<String>();
            Map<String,String> seqs_identitys = new HashMap<String,String>();
            for (String child : childrens) {
                String splits[] = child.split("-");
                seqs.add(splits[1]);
                //identitys.add(splits[0]);
                seqs_identitys.put(splits[1], splits[0]);
                if (identity.equals(splits[0]))
                    currentSeq = splits[1];
            }

            List<String> sortSeqs = new ArrayList<String>();
            sortSeqs.addAll(seqs);
            Collections.sort(sortSeqs);

            // 第一个节点，则无论是读锁还是写锁都可以获取
            if (currentSeq.equals(sortSeqs.get(0))) {
                res = true;
                return res;
            } else {
                // 写锁
                if (type == 1) {
                    res = false;
                    //第一次请求取锁则设置监听，以后就不设置了，因为监听一直存在
                    if(reps==false)
                        addChildWatcher("/ShardLockDemo");
                    return res;
                }
            }
            // int index =-1;
            boolean hasW = true;
            for (String seq : sortSeqs) {
                // ++index;
                if (seq.equals(currentSeq)) {
                    break;
                }
                if (!seqs_identitys.get(seq).startsWith("W"))
                    hasW = false;
            }
            if (type == 0 && hasW == false) {
                res = true;
            } else if (type == 0 && hasW == true) {
                res = false;
            }
            if (res == false) {
                // 添加监听
                addChildWatcher("/ShardLockDemo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean unlockForExclusive() {
        try {
            if (client.checkExists().forPath("/ExclusiveLockDemo/lock") != null) {
                client.delete().forPath("/ExclusiveLockDemo/lock");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean unlockForShardLock() {
        try {
            if (client.checkExists().forPath(selfNodeName) != null) {
                client.delete().forPath(selfNodeName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void addChildWatcher(String path) throws Exception {
        @SuppressWarnings("resource")
        final PathChildrenCache cache = new PathChildrenCache(client, path,
                true);
        cache.start(StartMode.POST_INITIALIZED_EVENT);// ppt中需要讲StartMode
        // System.out.println(cache.getCurrentData().size());
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client,
                                   PathChildrenCacheEvent event) throws Exception {
                if (event.getType().equals(
                        PathChildrenCacheEvent.Type.INITIALIZED)) {

                } else if (event.getType().equals(
                        PathChildrenCacheEvent.Type.CHILD_ADDED)) {

                } else if (event.getType().equals(
                        PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    String path = event.getData().getPath();
                    System.out.println("收到监听"+path);
                    if(path.contains("ExclusiveLockDemo")){
                        latch.countDown();
                    }else if(path.contains("ShardLockDemo")){
                        //收到自己的通知就不处理
                        if(path.contains(selfIdentity))
                            return;
                        List<String> lockChildrens = client.getChildren().forPath(
                                "/ShardLockDemo");
                        boolean isLock = false;
                        try{
                            if(selfIdentity.startsWith("R"))
                                isLock = canGetLock(lockChildrens,0,selfIdentity.substring(0, selfIdentity.length() - 1),true);
                            else if(selfIdentity.startsWith("W"))
                                isLock = canGetLock(lockChildrens,1,selfIdentity.substring(0, selfIdentity.length() - 1),true);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        if(isLock){
                            //获得锁
                            shardLocklatch.countDown();
                        }
                    }
                } else if (event.getType().equals(
                        PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                }
            }
        });
    }
}
