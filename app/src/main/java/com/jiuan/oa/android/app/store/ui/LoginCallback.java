package com.jiuan.oa.android.app.store.ui;

import com.jiuan.oa.android.library.http.login.OALoginResponse;

public interface LoginCallback {

    /**
     * 登录成功
     */
    void onLoginSuccess(OALoginResponse oaLoginResponse);

    /**
     * 注销状态
     */
    void Logout();
}
