package com.jiuan.oa.android.app.store.noticehttplibrary;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/6/30.
 */
public class ReturnValueResponse {
    @SerializedName("List")
    private String noticeList;

    @SerializedName("lastSize")
    private int lastSize;


    public int getLastSize() {
        return lastSize;
    }

    public void setLastSize(int lastSize) {
        this.lastSize = lastSize;
    }

    public void setNoticeList(String noticeList) {
        this.noticeList = noticeList;
    }

    public String getNoticeList() {
        return noticeList;
    }
}
