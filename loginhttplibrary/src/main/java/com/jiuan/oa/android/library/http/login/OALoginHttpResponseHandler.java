package com.jiuan.oa.android.library.http.login;

import com.jiuan.oa.android.library.http.OAHttpResponseHandler;

import android.util.Log;

public class OALoginHttpResponseHandler extends OAHttpResponseHandler {

    // 用户名或密码错误
    private static final String FAILURE_201 = "201";

    // 用户名不可以为空
    private static final String FAILURE_201_3 = "201.3";

    // 密码不可以为空
    private static final String FAILURE_201_4 = "201.4";

    // 用户已离职
    private static final String FAILURE_202 = "202";

    @Override
    public void onOASuccess(String value) {
        onLoginSuccess(get(value,OALoginResponse.class));
    }

    @Override
    public void onOAFailure(String msg) {
        if (msg.equals(FAILURE_201)) {
            onLoginFailure("用户名或密码错误");
        } else if (msg.equals(FAILURE_201_3)) {
            onLoginFailure("用户名不可以为空");
        } else if (msg.equals(FAILURE_201_4)) {
            onLoginFailure("密码不可以为空");
        } else if (msg.equals(FAILURE_202)) {
            onLoginFailure("用户已离职");
        }
    }

    public void onLoginSuccess(OALoginResponse oaLoginResponse) {
        Log.d("getAccessKey", "" + oaLoginResponse.getAccessKey());
        Log.d("getAccount", "" + oaLoginResponse.getAccount());
        Log.d("getPassword", "" + oaLoginResponse.getPassword());
        Log.d("getUserID", "" + oaLoginResponse.getUserID());
        Log.d("getUserName", "" + oaLoginResponse.getUserName());
        Log.d("getUserType", "" + oaLoginResponse.getUserType());
        Log.d("getUserMnemonicCode", "" + oaLoginResponse.getUserMnemonicCode());
        Log.d("getDepartmentID", "" + oaLoginResponse.getDepartmentID());
        Log.d("getDepartmentName", "" + oaLoginResponse.getDepartmentName());
        Log.d("getDepartmentMnemonicCode", "" + oaLoginResponse.getDepartmentMnemonicCode());
        Log.d("getCompanyID", "" + oaLoginResponse.getCompanyID());
        Log.d("getCompanyName", "" + oaLoginResponse.getCompanyName());
        Log.d("getCompanyMnemonicCode", "" + oaLoginResponse.getCompanyMnemonicCode());
    }

    public void onLoginFailure(String msg) {
        Log.d("onLoginFailure", "" + msg);
    }

}
