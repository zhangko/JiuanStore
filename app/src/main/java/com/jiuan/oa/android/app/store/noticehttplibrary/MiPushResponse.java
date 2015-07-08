package com.jiuan.oa.android.app.store.noticehttplibrary;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/7/6.
 */
public class MiPushResponse {
    @SerializedName("Message")
    private String message;

    @SerializedName("Type")
    private int type;

    @SerializedName("Content")
    private String content;

    @SerializedName("EmployeeID")
    private String employeeID;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getMessage() {
        return message;
    }

}
