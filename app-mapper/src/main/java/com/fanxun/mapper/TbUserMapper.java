package com.fanxun.mapper;

import java.util.List;

import com.fanxun.pojo.TbUser;
import com.fanxun.pojo.TbUserExample;
import com.fanxun.pojo.UserInfoToPage;
import org.apache.ibatis.annotations.Param;

public interface TbUserMapper {
    int countByExample(TbUserExample example);

    int deleteByExample(TbUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    List<TbUser> selectByExample(TbUserExample example);

    TbUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByExample(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);

    //自定制查询所有用户信息，其中不需要查询用户密码和用户创建时间
    List<UserInfoToPage> selectAllUsers();
}