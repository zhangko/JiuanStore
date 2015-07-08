package com.jiuan.oa.android.app.store.util;

import com.jiuan.oa.android.library.http.store.StoreClient;
import com.jiuan.oa.android.library.http.store.StoreSearchBody;
import com.jiuan.oa.android.library.http.store.StoreSearchHttpResponseHandler;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

public class DownloadUtils {

    public static void checkVersion(final Context context, final String appName) {
        Log.d("DownloadcheckVersion","checkVersion");
        StoreClient.requestSearch(context, appName, new StoreSearchHttpResponseHandler() {

            @Override
            public void onStoreSearchSuccess(StoreSearchBody[] storeSearchBody) {
                Log.d("onStoreSearchSuccess","onStoreSearchSuccess");
                super.onStoreSearchSuccess(storeSearchBody);
                String packageName = null;
                int versionCode = 1;
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    packageName = packageInfo.packageName;
                    versionCode = packageInfo.versionCode;
                    Log.d("versionCode"," " + versionCode);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                for (StoreSearchBody body : storeSearchBody) {
                    Log.d("StoreSearchBody","bodyname" + body.getName());
                    Log.d("StoreSearchBody","bodyid" + body.getId());
                    Log.d("StoreSearchBody","identifier" +  body.getAppIdentifier());
                    Log.d("StoreSearchBody","packageName" + packageName);
                    Log.d("StoreSearchBody","versioncode" + body.getVersion());

                    if (update(context, body, appName, packageName, versionCode)) {
                        Log.d("StoreSearchBody","StoreSearchBody");
                        break;
                    }
                }
            }
        });
    }

    private static boolean update(final Context context, final StoreSearchBody body, final String appName, String packageName, int versionCode) {
        if (body.getId() != null && body.getId().equals(appName)
                && body.getAppIdentifier() != null && body.getAppIdentifier().equals(packageName)
                && body.getVersion() != null && Integer.parseInt(body.getVersion()) > versionCode) {
            Log.d("update","发现新版本");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("升级").setMessage("发现新的版本")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            download(context, body.getDownLoadPath(), appName, DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED, ~0);
                        }
                    })
                    .setNegativeButton("取消", null).show();
            return true;
        }
        return false;
    }

    public static long download(Context context, String url, String appName, int visibility, int type) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir("Download", appName + ".apk");
        request.setTitle(appName);
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(visibility);
        request.setAllowedNetworkTypes(type);
        return manager.enqueue(request);
    }

    /**
     * 获得正在下载的ID
     *
     * @param uri 下载地址
     */
    public static long getDownloadID(Context context, String uri) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(DownloadManager.STATUS_RUNNING | DownloadManager.STATUS_PENDING);
        Cursor c = manager.query(query);
        if (c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI)).equals(uri)) {
                    long id = c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID));
                    c.close();
                    return id;
                }
            } while (c.moveToNext());
        }
        c.close();
        return -1;
    }

    /**
     * 获得当前的连接状态
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

}
