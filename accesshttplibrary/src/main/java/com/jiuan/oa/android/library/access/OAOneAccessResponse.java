package com.jiuan.oa.android.library.access;

import com.google.gson.annotations.SerializedName;

public class OAOneAccessResponse {

    @SerializedName("YesOrNo")
    private int hasAccess;

    public int getHasAccess() {
        return hasAccess;
    }

    public void setHasAccess(int hasAccess) {
        this.hasAccess = hasAccess;
    }
}
