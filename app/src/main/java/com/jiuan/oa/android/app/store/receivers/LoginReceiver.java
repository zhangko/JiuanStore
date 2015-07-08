package com.jiuan.oa.android.app.store.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jiuan.oa.android.app.store.JiuanStoreApplication;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeClient;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeResponseHandler;
import com.jiuan.oa.android.app.store.ui.MainActivity;
import com.jiuan.oa.android.library.http.login.OALoginResponse;

/**
 * Created by ZhangKong on 2015/7/6.
 */
public class LoginReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("MSG", "  " + "收到消息了！");
        Bundle bundle = intent.getExtras();
        OALoginResponse info = bundle.getParcelable("OALoginResponse");
        Log.v("INFO","  " + info.toString());
        String token = JiuanStoreApplication.LoadToken(context);
        if (TextUtils.isEmpty(token)) {
            Log.v("token值是：", "未获得");
            return;
        } else {
            Log.v("token值是：", token);
        }

        NoticeClient.requestToken(context, info.getAccount(), info.getAccount(), info.getAccessKey(), token, info.getUserID(), 2, new NoticeResponseHandler() {
            @Override
            public void onOASuccess(String value) {
                Log.d("NoticeClient", " 请求小米推送请求");


            }
        });
    }

}
