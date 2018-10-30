package com.fanxun.mapper;

import com.fanxun.pojo.TbMessage;
import com.fanxun.pojo.TbMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbMessageMapper {
    int countByExample(TbMessageExample example);

    int deleteByExample(TbMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbMessage record);

    int insertSelective(TbMessage record);

    List<TbMessage> selectByExample(TbMessageExample example);

    TbMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbMessage record, @Param("example") TbMessageExample example);

    int updateByExample(@Param("record") TbMessage record, @Param("example") TbMessageExample example);

    int updateByPrimaryKeySelective(TbMessage record);

    int updateByPrimaryKey(TbMessage record);

    //自定义添加方法,通过用户id查询其拥有得消息列表
    List<TbMessage> selectBySubscribeUserId(@Param("subscribeUserId") String subscribeUserId,@Param("messageType") String messageType);

    //自定义添加方法，批量插入消息
    int insertBatch(@Param("message") TbMessage message,@Param("subscribeUserIds") List<String> subscribeUserIds);

}