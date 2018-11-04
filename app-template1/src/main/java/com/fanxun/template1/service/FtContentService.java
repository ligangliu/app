package com.fanxun.template1.service;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.pojo.TbFtContent;

/**
 * @Author liu
 * @Date 2018-11-01 23:25
 */
public interface FtContentService {
    /**
     * 1.插入一个文章
     *      入参数，TbFtContent content
     * 2.更新文章内容
     *      入参数，TbFtContent content
     * 3.根据appid分页查看所有文章，把文章标题分页显示出来
     *      传入参数  Integer appId
     * 4.根据appId设置某个小程序默认显示文章
     *      传入参数  Integer appId,Integer cid
     * 5.根据appId获取其默认的文章
     *      传入参数：Integer appId
     * @param content
     * @return
     */
    //插入一个文章
    public FanXunResult insertContent(TbFtContent content);
    //根据appid分页查看所有文章，把文章标题分页显示出来
    public FanXunResult getAllContentsByAppid(Integer appId,Integer uid,Integer page, Integer row);
    //根据文章cid查看某个文章详细内容
    public FanXunResult getContentByCid(Integer cid);
    //根据appId设置某个小程序默认显示文章
    public FanXunResult updateContentSelectd(Integer appId,Integer uid,Integer cid,Integer originSelectedCid);
    //根据appId获取其默认的文章
    public FanXunResult getContentSelectd(Integer appId,Integer uid);
    //更新文章内容
    public FanXunResult updateContent(TbFtContent content);
}
