package com.jiuan.oa.android.app.store.noticehttplibrary;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/7/7.
 */
public class NoticeTitleResponse {
    @SerializedName("TitleID")
    private String titleID;

    public void setTitleID(String titleID) {
        this.titleID = titleID;
    }

    public String getTitleID() {
        return titleID;
    }
}
