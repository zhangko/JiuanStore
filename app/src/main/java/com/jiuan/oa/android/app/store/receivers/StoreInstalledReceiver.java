package com.jiuan.oa.android.app.store.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StoreInstalledReceiver extends BroadcastReceiver {

    private InstalledListener mInstalledListener;

    public StoreInstalledReceiver(InstalledListener installedListener) {
        mInstalledListener = installedListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getDataString();
            try {
                String temp = packageName.substring(8, 32);
                if (temp.equals("com.jiuan.oa.android.app")) {
                    if (mInstalledListener != null) {
                        mInstalledListener.installComplete(context, packageName);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public interface InstalledListener {

        void installComplete(Context context, String packageName);
    }

}
