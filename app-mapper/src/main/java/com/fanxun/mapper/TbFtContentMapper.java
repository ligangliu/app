package com.fanxun.mapper;

import java.util.List;

import com.fanxun.pojo.FtContentToPage;
import com.fanxun.pojo.TbFtContent;
import com.fanxun.pojo.TbFtContentExample;
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
    //自定义函数
    public List<FtContentToPage> selectAllContents(@Param("appId") String appId, @Param("uid") Integer uid);
    //自定义函数
    public int updateIsSelectdByCid(@Param("cid") Integer cid,@Param("isSelected") Integer isSelected);
}