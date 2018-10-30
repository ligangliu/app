package com.fanxun.sso.service.impl;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.DateUtil;
import com.fanxun.common.utils.ExceptionUtil;
import com.fanxun.mapper.TbUserinfoMapper;
import com.fanxun.pojo.TbUserinfo;
import com.fanxun.pojo.TbUserinfoExample;
import com.fanxun.sso.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liu
 * @Date 2018-10-27 20:17
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private TbUserinfoMapper userinfoMapper;

    @Override
    public FanXunResult createUserInfo(TbUserinfo userinfo) {
        TbUserinfoExample example = new TbUserinfoExample();
        TbUserinfoExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(userinfo.getPhone());
        List<TbUserinfo> userinfos = userinfoMapper.selectByExample(example);
        if (userinfos != null && userinfos.size() >0){
            return FanXunResult.build(3000,"预约失败，该手机号已预约");
        }
        try {
            if (userinfo.getStatus() == null){
                userinfo.setStatus(1);
            }
            userinfo.setSubmitTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
            userinfoMapper.insert(userinfo);
            return FanXunResult.build(1000,"预约成功");
        } catch (Exception e) {
            return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
        }
    }

    @Override
    public FanXunResult getUserByPhone(String phone) {
        return null;
    }
}
