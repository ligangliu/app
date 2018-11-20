package com.fanxun.template1.dao;

import com.fanxun.pojo.TbUser;

/**
 * @Author liu
 * @Date 2018-11-13 12:17
 */
public interface TokenDao {
    public TbUser getUserByToken(String token);
}
