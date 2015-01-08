package com.jiuan.oa.android.app.store.ui;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.jiuan.oa.android.app.store.receivers.ExitReceiver;
import com.jiuan.oa.android.library.http.OAClient;
import com.jiuan.oa.android.library.protocol.login.LoginProtocol;

public abstract class BaseActivity extends ActionBarActivity {

    // 退出消息接收器
    private ExitReceiver mExitReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerExitReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelRequests();
        unregisterExitReceiver();
    }

    /**
     * 注册退出广播接收器
     */
    private void registerExitReceiver() {
        mExitReceiver = new ExitReceiver();
        IntentFilter intentFilter = new IntentFilter(LoginProtocol.ACTION_EXIT);
        registerReceiver(mExitReceiver, intentFilter);
    }

    /**
     * 注销退出广播接收器
     */
    private void unregisterExitReceiver() {
        unregisterReceiver(mExitReceiver);
    }

    /**
     * 取消请求
     */
    private void cancelRequests() {
        OAClient.getInstance().cancelRequests(this, true);
    }
}
