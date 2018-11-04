package com.fanxun.mapper;

import com.fanxun.pojo.FtContentToPage;
import com.fanxun.pojo.TbFtContent;
import com.fanxun.pojo.TbFtContentExample;
import java.util.List;

import com.fanxun.pojo.UserInfoToPage;
import org.apache.ibatis.annotations.Param;

public interface TbFtContentMapper {
    int countByExample(TbFtContentExample example);

    int deleteByExample(TbFtContentExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(TbFtContent record);

    int insertSelective(TbFtContent record);

    List<TbFtContent> selectByExample(TbFtContentExample example);

    TbFtContent selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") TbFtContent record, @Param("example") TbFtContentExample example);

    int updateByExample(@Param("record") TbFtContent record, @Param("example") TbFtContentExample example);

    int updateByPrimaryKeySelective(TbFtContent record);

    int updateByPrimaryKey(TbFtContent record);

    //自定制查询所有用户信息，其中不需要查询用户密码和用户创建时间
    List<FtContentToPage> selectAllContents(@Param("appId")Integer appId,@Param("uid") Integer uid);

    //根据appid和cid自定义更新is_select字段
    int updateIsSelectdByCid(@Param("cid") Integer cid,@Param("isSelected") Integer isSelected);

    //根据appid和cid

}