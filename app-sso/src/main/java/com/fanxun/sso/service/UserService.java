package com.fanxun.sso.service;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.pojo.TbUser;

/**
 * @Author liu
 * @Date 2018-10-10 13:42
 */
public interface UserService {
    public FanXunResult checkData(String content, int type);
    public FanXunResult createUser(TbUser user, String verifyCode, String send_verifyCode);
    public FanXunResult userLogin(String phone, String password);
    public FanXunResult getUserByToken(String token);
    public FanXunResult userLogout(String token);
    public String sendMessage(String phone);
    public FanXunResult forgetPassword(String phone,String password,String verifyCode,String send_verifyCode);
}
