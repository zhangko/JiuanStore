package com.jiuan.oa.android.library.access;

import android.util.Log;

import com.jiuan.oa.android.library.http.OAHttpResponseHandler;

public class OAAccessHttpResponseHandler extends OAHttpResponseHandler {

    // AccessKey错误
    private static final String FAILURE_201_1 = "201.1";

    // AccessKey过期
    private static final String FAILURE_201_2 = "201.2";

    @Override
    public void onOAFailure(String msg) {
        if (msg.equals(FAILURE_201_1)) {
            onAccessFailure("AccessKey错误");
        } else if (msg.equals(FAILURE_201_2)) {
            onAccessFailure("AccessKey过期");
        }
    }

    public void onAccessFailure(String msg) {
        Log.d("onAccessFailure", msg);
    }
}
