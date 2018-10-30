package com.fanxun.mapper;

import com.fanxun.pojo.TbUserinfo;
import com.fanxun.pojo.TbUserinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbUserinfoMapper {
    int countByExample(TbUserinfoExample example);

    int deleteByExample(TbUserinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbUserinfo record);

    int insertSelective(TbUserinfo record);

    List<TbUserinfo> selectByExampleWithBLOBs(TbUserinfoExample example);

    List<TbUserinfo> selectByExample(TbUserinfoExample example);

    TbUserinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbUserinfo record, @Param("example") TbUserinfoExample example);

    int updateByExampleWithBLOBs(@Param("record") TbUserinfo record, @Param("example") TbUserinfoExample example);

    int updateByExample(@Param("record") TbUserinfo record, @Param("example") TbUserinfoExample example);

    int updateByPrimaryKeySelective(TbUserinfo record);

    int updateByPrimaryKeyWithBLOBs(TbUserinfo record);

    int updateByPrimaryKey(TbUserinfo record);
}