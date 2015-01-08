package com.jiuan.oa.android.app.store.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DownloadReceiver extends BroadcastReceiver {

    private DownloadListener mDownloadListener;

    public DownloadReceiver(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (mDownloadListener != null && downloadId != -1) {
                mDownloadListener.downloadComplete(context, downloadId);
            }
        }
    }

    public interface DownloadListener {

        void downloadComplete(Context context, long downloadId);
    }
}
