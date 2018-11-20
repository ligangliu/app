package com.fanxun.sso.service.impl;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.*;
import com.fanxun.mapper.TbUserMapper;
import com.fanxun.pojo.TbUser;
import com.fanxun.pojo.TbUserExample;
import com.fanxun.pojo.UserInfoToPage;
import com.fanxun.sso.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @Author liu
 * @Date 2018-10-10 13:43
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${REDIS_SESSION_EXPIRE}")
    private Integer REDIS_SESSION_EXPIRE;


    /**
     * 对用户数据进行检验，检查某个用户名或者phone是否可用
     * type 1,2分表表示content=phone,content=username
     * @param content
     * @param type
     * @return
     */
    @Override
    public FanXunResult checkData(String content, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();

        if (1 == type){
            criteria.andPhoneEqualTo(content);
        }else if (2 == type){
            criteria.andUsernameEqualTo(content);
        }
        try {
            List<TbUser> users = userMapper.selectByExample(example);
            //表示数据可用
            if (users == null || users.size() == 0){
                return FanXunResult.build(1001,"不存在此记录");
            }
            return FanXunResult.build(1002,"存在此记录");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }


    }

    @Override
    public FanXunResult createUser(TbUser user,String verifyCode,String send_verifyCode) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(user.getPhone());
        List<TbUser> users = null;
        try {
            users = userMapper.selectByExample(example);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }
        if (users != null && users.size() >0){
            return FanXunResult.build(3000,"该手机号已注册");
        }
        user.setRegisterDate(new Date());
        //使用spring框架提供的MD5加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        try {
            if (send_verifyCode.equals(DigestUtils.md5DigestAsHex(verifyCode.getBytes()))){
                try {
                    userMapper.insert(user);
                    return FanXunResult.build(1000,"该用户注册成功");
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return FanXunResult.build(3000,"数据库异常");
                }

            }else {
                return FanXunResult.build(3000, "验证码错误");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return FanXunResult.build(3000, "数据库异常");
        }
    }

    @Override
    public FanXunResult forgetPassword(String phone, String password, String verifyCode, String send_verifyCode) {
        //userMapper.updateByExampleSelective()
        try {
            if (send_verifyCode.equals(DigestUtils.md5DigestAsHex(verifyCode.getBytes()))){
                TbUserExample example = new TbUserExample();
                TbUserExample.Criteria criteria = example.createCriteria();
                criteria.andPhoneEqualTo(phone);
                List<TbUser> users = userMapper.selectByExample(example);
                if (null == users || users.size() == 0) {
                    return FanXunResult.build(3000, "该手机号不存在");
                }
                TbUser user = users.get(0);
                user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
                userMapper.updateByExampleSelective(user,example);
                return FanXunResult.build(1000,"更新密码成功");
            }else {
                return FanXunResult.build(3000,"验证码错误");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }

    }

    @Override
    public FanXunResult userLogin(String phone, String password) {
        try {
            TbUserExample example = new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();
            criteria.andPhoneEqualTo(phone);
            List<TbUser> users = userMapper.selectByExample(example);
            //如果没有此用户
            if (null == users || users.size() == 0) {
                return FanXunResult.build(3000, "该手机号不存在");
            }
            TbUser user = users.get(0);
            //比对密码
            if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
                return FanXunResult.build(3000, "密码错误");
            }
            //生成token
            String token = UUID.randomUUID().toString();
            //把用户信息写入redis中
            String key = REDIS_USER_SESSION_KEY+":"+token;
            //存放在redis中的用户信息，为了安全，不要存入密码信息
            //保存用户之前，将用户密码清空
            user.setPassword(null);
            JedisUtil.set(key, JsonUtil.objectToJson(user));
            JedisUtil.expire(key,REDIS_SESSION_EXPIRE);

            //将token返回
            HashMap<String, String> map_token = new HashMap<>();
            map_token.put("token",token);
            map_token.put("username",user.getUsername());
            return FanXunResult.ok(map_token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return FanXunResult.build(3000, "数据库异常");
        }
    }

    @Override
    public FanXunResult getUserByToken(String token) {
        try {
            String key = REDIS_USER_SESSION_KEY + ":" +token;
            String json = JedisUtil.get(key);
            //判断是否为空
            if (StringUtils.isBlank(json)) {
                return FanXunResult.build(1003, "该token已经过期或不存在");
            }
            //更新过期时间
            JedisUtil.expire(key,REDIS_SESSION_EXPIRE);
            //返回用户信息
            return FanXunResult.ok(JsonUtil.jsonToPojo(json, TbUser.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return FanXunResult.build(3000, "数据库异常");
        }
    }

    @Override
    public FanXunResult refreshByToken(String token) {
        try {
            String key = REDIS_USER_SESSION_KEY + ":" +token;
            String json = JedisUtil.get(key);
            //判断是否为空
            if (StringUtils.isBlank(json)) {
                return FanXunResult.build(1003, "该token已经过期或不存在");
            }
            //更新过期时间
            JedisUtil.expire(key,REDIS_SESSION_EXPIRE);
            //返回用户信息
            return FanXunResult.build(1000,"页面刷新成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return FanXunResult.build(3000, "数据库异常");
        }
    }

    /**
     * 用户根据token信息退出
     * @param token
     * @return
     */
    @Override
    public FanXunResult userLogout(String token) {
        try {
            String key = REDIS_USER_SESSION_KEY + ":" +token;
            int reslult = (int) JedisUtil.del(key);
            if (reslult == 1){
                return FanXunResult.build(1000,"用户退出成功");
            }else {
                return FanXunResult.build(1003,"该token已经过期或不存在");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return FanXunResult.build(3000, "redis服务异常");
        }
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @Override
    public String sendMessage(String phone) {
        String verifyCode = VerifyCodeUtil.getVerifyCode();
        String result = MessageUtil.send(phone,verifyCode);
        if (null != result && result.equals("OK")){
            return verifyCode;
        }else {
            return null;
        }
    }

    /**
     * 分页查询所有用户信息,并用于页面的显示
     * @param page
     * @param row
     * @return
     */
    @Override
    public FanXunResult getAllUserInfo(Integer page,Integer row) {
        try {
            PageHelper.startPage(page,row);
            List<UserInfoToPage> userInfoToPages = userMapper.selectAllUsers();
            PageInfo<UserInfoToPage> pageInfo = new PageInfo<>(userInfoToPages);
            return FanXunResult.build(1000,"OK",pageInfo);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000, "redis服务异常");
        }
    }
}
