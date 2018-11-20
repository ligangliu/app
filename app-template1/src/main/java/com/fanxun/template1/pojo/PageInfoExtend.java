package com.fanxun.template1.pojo;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author liu
 * @Date 2018-11-02 20:41
 */
public class PageInfoExtend<T> extends PageInfo<T> {

    public PageInfoExtend(List<T> list, Integer lastSelectedCid) {
        super(list);
        this.lastSelectedCid = lastSelectedCid;
    }

    private Integer lastSelectedCid;

    public Integer getLastSelectedCid() {
        return lastSelectedCid;
    }

    public void setLastSelectedCid(Integer lastSelectedCid) {
        this.lastSelectedCid = lastSelectedCid;
    }
}
