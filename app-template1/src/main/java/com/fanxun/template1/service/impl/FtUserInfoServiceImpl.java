package com.fanxun.template1.service.impl;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.DateUtil;
import com.fanxun.common.utils.ExceptionUtil;
import com.fanxun.mapper.TbFtUserinfoMapper;
import com.fanxun.pojo.TbFtUserinfo;
import com.fanxun.pojo.TbFtUserinfoExample;
import com.fanxun.pojo.TbUserinfo;
import com.fanxun.pojo.UserInfoToPage;
import com.fanxun.template1.service.FtUserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liu
 * @Date 2018-11-13 15:04
 */
@Service
public class FtUserInfoServiceImpl implements FtUserInfoService {

    @Autowired
    private TbFtUserinfoMapper userinfoMapper;

    @Override
    public FanXunResult createFtUserInfo(TbFtUserinfo userinfo) {
        if (null == userinfo){
            return FanXunResult.build(3000,"报名用户信息不能为空");
        }
        try {
            TbFtUserinfoExample example = new TbFtUserinfoExample();
            TbFtUserinfoExample.Criteria criteria = example.createCriteria();
            criteria.andCidEqualTo(userinfo.getCid());
            criteria.andPhoneEqualTo(userinfo.getPhone());
            List<TbFtUserinfo> result = userinfoMapper.selectByExample(example);
            if (result != null && result.size() >0){
                return FanXunResult.build(3000,"该手机号已报名");
            }
            userinfo.setSubmitTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
            userinfoMapper.insert(userinfo);
            return FanXunResult.build(1000,"用户报名成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000, "数据库异常");
        }
    }

    @Override
    public FanXunResult getAllFtUserInfo(Integer cid,Integer page, Integer row) {
        try {
            PageHelper.startPage(page,row);
            TbFtUserinfoExample example = new TbFtUserinfoExample();
            TbFtUserinfoExample.Criteria criteria = example.createCriteria();
            criteria.andCidEqualTo(cid);
            List<TbFtUserinfo> userinfos = userinfoMapper.selectByExample(example);
            PageInfo<TbFtUserinfo> pageInfo = new PageInfo<>(userinfos);
            return FanXunResult.build(1000,"OK",pageInfo);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }
    }

}
