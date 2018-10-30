package com.fanxun.sso.service;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.pojo.TbUserinfo;

/**
 * @Author liu
 * @Date 2018-10-27 20:15
 */
public interface UserInfoService {
    public FanXunResult createUserInfo(TbUserinfo userinfo);
    public FanXunResult getUserByPhone(String phone);
}
