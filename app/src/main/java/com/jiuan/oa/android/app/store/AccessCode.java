package com.jiuan.oa.android.app.store;

import com.google.gson.annotations.SerializedName;

public class AccessCode {

    @SerializedName("Code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
