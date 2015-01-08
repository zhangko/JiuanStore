package com.jiuan.oa.android.app.store.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiuan.oa.android.library.protocol.login.LoginProtocol;

public class ExitReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(LoginProtocol.ACTION_EXIT)) {
            Activity activity = (Activity) context;
            activity.finish();
        }
    }

}
