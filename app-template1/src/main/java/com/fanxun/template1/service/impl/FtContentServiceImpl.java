package com.fanxun.template1.service.impl;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.DateUtil;
import com.fanxun.mapper.TbFtContentMapper;
import com.fanxun.pojo.FtContentToPage;
import com.fanxun.pojo.TbFtContent;
import com.fanxun.pojo.TbFtContentExample;
import com.fanxun.template1.pojo.PageInfoExtend;
import com.fanxun.template1.service.FtContentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liu
 * @Date 2018-11-01 23:26
 */
@Service
public class FtContentServiceImpl implements FtContentService {

    @Autowired
    private TbFtContentMapper contentMapper;

    @Override
    public FanXunResult insertContent(TbFtContent content) {
        try {
            String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
            content.setCreateTime(date);
            content.setIsSelected(0);
            content.setIsDeleted(0);
            contentMapper.insert(content);
            return FanXunResult.build(1000,"文章插入成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000, "数据库异常");
        }
    }
    @Override
    public FanXunResult updateContent(TbFtContent content) {
        try{
            contentMapper.updateByPrimaryKeySelective(content);
            return FanXunResult.build(1000,"文章更新成功");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }
    }

    @Override
    public FanXunResult getAllContentsByAppid(String appId, Integer uid, Integer page, Integer row) {
        try {
            PageInfoExtend<FtContentToPage> pagePageInfo = null;
            //需要找出数据库中目前isSelected为1的数据
            TbFtContentExample example = new TbFtContentExample();
            TbFtContentExample.Criteria criteria = example.createCriteria();
            criteria.andAppidEqualTo(appId);
            criteria.andUidEqualTo(uid);
            criteria.andIsSelectedEqualTo(1);
            List<TbFtContent> list = contentMapper.selectByExample(example);
            Integer lastSelectedCid = null;
            if (list != null && list.size() > 0){
                System.out.println(list.size());
                lastSelectedCid = list.get(0).getCid();
            }
            PageHelper.startPage(page,row);
            List<FtContentToPage> ftContentToPages = contentMapper.selectAllContents(appId,uid);
            pagePageInfo = new PageInfoExtend<>(ftContentToPages,lastSelectedCid);
            return FanXunResult.build(1000,"OK", pagePageInfo);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }
    }

    @Override
    public FanXunResult getContentByCid(Integer cid) {
        try {
            TbFtContent content = contentMapper.selectByPrimaryKey(cid);
            return FanXunResult.build(1000,"OK", content);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }
    }

    @Override
    public FanXunResult updateContentSelectd(Integer cid,Integer lastSelectedCid) {
        try{

            //如果传过来的原来那个cid不为空，则将用于原来选择的isSelected置为0，让用户重新选择。
            if (lastSelectedCid != null){
                if (cid == lastSelectedCid){
                    return FanXunResult.build(1000,"文章选择成功");
                }
                contentMapper.updateIsSelectdByCid(lastSelectedCid,0);
            }
          //  TbFtContent content = contentMapper.selectByPrimaryKey(cid);
//            //验证用户的合法性，如果用户id与小程序id与文章中的用户id以及小程序id不一致的话就是非法用户
//            if (!(appId == content.getAppid() && uid == content.getUid())){
//                return FanXunResult.build(3000,"文章id与用户id不匹配");
//            }
            contentMapper.updateIsSelectdByCid(cid,1);
            return FanXunResult.build(1000,"文章选择成功");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }
    }

    @Override
    public FanXunResult getContentSelectd(String appId) {
        try {
            TbFtContentExample example = new TbFtContentExample();
            TbFtContentExample.Criteria criteria = example.createCriteria();
            //criteria.andUidEqualTo(uid);
            criteria.andAppidEqualTo(appId);
            criteria.andIsSelectedEqualTo(1);
            List<TbFtContent> result = contentMapper.selectByExample(example);
            if (result != null && result.size() > 0){
                TbFtContent content = result.get(0);
                return FanXunResult.build(1000,"OK",content);
            }else {
                return FanXunResult.build(3000,"用户还未为该小程序选择默认文章");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return FanXunResult.build(3000,"数据库异常");
        }
    }


}
