package com.jiuan.oa.android.app.store.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.library.protocol.login.LoginProtocol;
import com.jiuan.oa.android.library.ui.login.LoginActivity;

public class InstalledReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED) || intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getDataString();
            try {
                packageName = packageName.substring(8, 32);
                if (packageName.equals("com.jiuan.oa.android.app")) {
                    sendBroadcast(context);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendBroadcast(Context context) {
        SharedPreferences mPreferences = context.getSharedPreferences(LoginActivity.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("OALoginResponse", "");
        OALoginResponse info = gson.fromJson(json, OALoginResponse.class);
        if (info != null) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable("OALoginResponse", info);
            intent.putExtras(bundle);
            intent.setAction(LoginProtocol.ACTION_LOGIN_SUCCESS);
            if (android.os.Build.VERSION.SDK_INT >= 12) {
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            }
            context.sendOrderedBroadcast(intent, LoginProtocol.PERMISSIONS_LOGIN);
        }
    }

}
