package com.jiuan.oa.android.app.store.noticehttplibrary;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/6/29.
 */
public class NoticeResponse {

    @SerializedName("TitleID")
    private String titleID;

    @SerializedName("TitleName")
    private String titleName;

    @SerializedName("Timestamp")
    private String timeStamp;

    @SerializedName("Contest")
    private String contest;

    @SerializedName("Tag")
    private int tag;



    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTitleID(String titleID) {
        this.titleID = titleID;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTitleID() {
        return titleID;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setContest(String contest) {
        this.contest = contest;
    }

    public String getContest() {
        return contest;
    }



    public void setTag(int tag) {
        this.tag = tag;
    }



    public int getTag() {
        return tag;
    }
}
