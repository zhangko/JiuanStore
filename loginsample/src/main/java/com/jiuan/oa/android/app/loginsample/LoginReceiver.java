package com.jiuan.oa.android.app.loginsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jiuan.oa.android.library.http.login.OALoginResponse;

public class LoginReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        OALoginResponse info = bundle.getParcelable("OALoginResponse");
        Log.d("LoginReceiver", "account "+info.getAccount());
        Log.d("LoginReceiver", "userID "+info.getUserID());
        Log.d("LoginReceiver", "accessKey "+info.getAccessKey());
    }

}
