package com.fanxun.template1.dao.impl;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.ExceptionUtil;
import com.fanxun.common.utils.JedisSentinelUtil;
import com.fanxun.common.utils.JedisUtil;
import com.fanxun.common.utils.JsonUtil;
import com.fanxun.pojo.TbUser;
import com.fanxun.template1.dao.TokenDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author liu
 * @Date 2018-11-13 12:21
 */

@Component
public class TokenDaoImpl implements TokenDao {

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Override
    public TbUser getUserByToken(String token) {
        try {
            String key = REDIS_USER_SESSION_KEY + ":" +token;
            String json = JedisUtil.get(key);
            //判断是否为空
            if (StringUtils.isBlank(json)) {
                return null;
            }
            //更新过期时间
          //  JedisUtil.expire(key,REDIS_SESSION_EXPIRE);
            //返回用户信息
            return JsonUtil.jsonToPojo(json, TbUser.class);
        } catch (Exception e) {
           return null;
        }
    }
}
