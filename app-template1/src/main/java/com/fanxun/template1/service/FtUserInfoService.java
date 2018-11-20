package com.fanxun.template1.service;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.pojo.TbFtUserinfo;

/**
 * @Author liu
 * @Date 2018-11-13 14:57
 */
public interface FtUserInfoService {
    public FanXunResult createFtUserInfo(TbFtUserinfo userinfo);
    public FanXunResult getAllFtUserInfo(Integer cid,Integer page,Integer row);

}
