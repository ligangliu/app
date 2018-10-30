package com.fanxun.pojo;

public class TbMessage {
    private Integer id;

    private String messageUuid;

    private Integer messageType;

    private String messageThema;

    private String messageContent;

    private Integer isAll;

    private String messageAddTime;

    private Integer messageAddUserid;

    private String subscribeUserid;

    //添加是否已读字段标志，在数据库中并无此字段
    private Integer isRead;

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageUuid() {
        return messageUuid;
    }

    public void setMessageUuid(String messageUuid) {
        this.messageUuid = messageUuid == null ? null : messageUuid.trim();
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageThema() {
        return messageThema;
    }

    public void setMessageThema(String messageThema) {
        this.messageThema = messageThema == null ? null : messageThema.trim();
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
    }

    public String getMessageAddTime() {
        return messageAddTime;
    }

    public void setMessageAddTime(String messageAddTime) {
        this.messageAddTime = messageAddTime == null ? null : messageAddTime.trim();
    }

    public Integer getMessageAddUserid() {
        return messageAddUserid;
    }

    public void setMessageAddUserid(Integer messageAddUserid) {
        this.messageAddUserid = messageAddUserid;
    }

    public String getSubscribeUserid() {
        return subscribeUserid;
    }

    public void setSubscribeUserid(String subscribeUserid) {
        this.subscribeUserid = subscribeUserid == null ? null : subscribeUserid.trim();
    }
}