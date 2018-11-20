package com.fanxun.mapper;

import com.fanxun.pojo.TbFtUserinfo;
import com.fanxun.pojo.TbFtUserinfoExample;
import java.util.List;

import com.fanxun.pojo.TbUser;
import com.fanxun.pojo.TbUserinfo;
import org.apache.ibatis.annotations.Param;

public interface TbFtUserinfoMapper {
    int countByExample(TbFtUserinfoExample example);

    int deleteByExample(TbFtUserinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbFtUserinfo record);

    int insertSelective(TbFtUserinfo record);

    List<TbFtUserinfo> selectByExample(TbFtUserinfoExample example);

    TbFtUserinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbFtUserinfo record, @Param("example") TbFtUserinfoExample example);

    int updateByExample(@Param("record") TbFtUserinfo record, @Param("example") TbFtUserinfoExample example);

    int updateByPrimaryKeySelective(TbFtUserinfo record);

    int updateByPrimaryKey(TbFtUserinfo record);

    //selectAllUsers
    //public List<TbUserinfo> SEL
}