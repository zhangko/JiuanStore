package com.jiuan.oa.android.app.store.ui.widget;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiuan.oa.android.app.store.AppInfo;
import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.app.store.receivers.DownloadReceiver;
import com.jiuan.oa.android.app.store.receivers.StoreInstalledReceiver;
import com.jiuan.oa.android.app.store.util.DownloadUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StoreHomeAdapter extends RecyclerView.Adapter<StoreHomeViewHolder> implements StoreHomeViewHolder.onItemClickListener, DownloadReceiver.DownloadListener, StoreInstalledReceiver.InstalledListener {

    private static final int INSTALL = 0;

    private static final int INSTALLING = 1;

    private static final int INSTALLED = 2;

    private static final int UPDATE = 3;

    private DownloadReceiver downloadReceiver;

    private StoreInstalledReceiver installedReceiver;

    private Context mContext;

    private ArrayList<AppInfo> mAppInfoList;

    private DisplayImageOptions options;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public static final String CATEGORY_JIUANSTORE_LAUNCHER = "android.intent.category.com.jiuan.oa.android.app.store.LAUNCHER";

    // Provide a suitable constructor (depends on the kind of dataset)
    public StoreHomeAdapter(Context contexts) {
        downloadReceiver = new DownloadReceiver(this);
        installedReceiver = new StoreInstalledReceiver(this);
        mContext = contexts;
        mAppInfoList = new ArrayList<AppInfo>();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher_play_store)
                .showImageOnFail(R.mipmap.ic_launcher_play_store)
                .showImageForEmptyUri(R.mipmap.ic_launcher_play_store)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StoreHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.storehome_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        StoreHomeViewHolder holder = new StoreHomeViewHolder(v);
        holder.setOnItemClickListener(this);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StoreHomeViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // 判断是否手机中是否存在该应用
        if (mAppInfoList == null || mAppInfoList.size() == 0) {
            return;
        }
        // 显示应用图标
        setIcon(holder, position);
        // 显示应用名称
        holder.name.setText((position + 1) + "." + " " + mAppInfoList.get(position).getAppName());
        // 显示应用版本
        holder.version.setText(mAppInfoList.get(position).getVersionName());
        // 显示应用状态
        setState(holder, position);
    }

    private void setIcon(StoreHomeViewHolder holder, int position) {
        // 异步加载图片
        ImageLoader.getInstance().displayImage(mAppInfoList.get(position).getIconUrl(), holder.icon, options, animateFirstListener);
    }

    private void setState(StoreHomeViewHolder holder, int position) {
        long id = DownloadUtils.getDownloadID(mContext, mAppInfoList.get(position).getDownloadUrl());
        if (id != -1) {
            holder.state.setText(mContext.getString(R.string.state_installing));
            setAppInfoState(position, INSTALLING, id);
            return;
        }

        if (checkBrowser(mAppInfoList.get(position).getPackageName())) {
            // 判断版本
            if (getVersion(mAppInfoList.get(position).getPackageName()) < mAppInfoList.get(position).getVersionCode()) {
                holder.state.setText(mContext.getString(R.string.state_update));
                setAppInfoState(position, UPDATE, -1);
            } else {
                holder.state.setText(mContext.getString(R.string.state_installed));
                setAppInfoState(position, INSTALLED, -1);
            }
        } else {
            holder.state.setText(mContext.getString(R.string.state_install));
            setAppInfoState(position, INSTALL, -1);
        }
    }

    private void setAppInfoState(int position, int state, long id) {
        mAppInfoList.get(position).setState(state);
        mAppInfoList.get(position).setDownloadId(id);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mAppInfoList == null || mAppInfoList.size() == 0) {
            return 0;
        }
        return mAppInfoList.size();
    }

    @Override
    public void onItemClick(int position) {
        if (mAppInfoList == null || mAppInfoList.size() == 0) {
            return;
        }

        switch (mAppInfoList.get(position).getState()) {
            case INSTALL:
                downloadOrUpdate(position);
                break;
            case INSTALLING:
                cancelDownloadOrUpdate(position);
                break;
            case INSTALLED:
                openApp(position);
                break;
            case UPDATE:
                downloadOrUpdate(position);
                break;
        }
    }

    @Override
    public void downloadComplete(Context context, long downloadId) {
        if (mAppInfoList == null || mAppInfoList.size() == 0) {
            return;
        }
        for (int i = 0; i < mAppInfoList.size(); i++) {
            if (mAppInfoList.get(i).getDownloadId() == downloadId) {
                DownloadManager mgr = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                Cursor c = mgr.query(new DownloadManager.Query().setFilterById(downloadId));
                if (c.moveToFirst()) {
                    String file = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    installAPK(file);
                }
                notifyDataSetChanged();
                break;
            }
        }
    }

    private void installAPK(String apkUrl) {
        if (apkUrl == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkUrl)), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    @Override
    public void installComplete(Context context, String packageName) {
        notifyDataSetChanged();
    }

    public void registerReceiver() {
        // 监听下载
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        mContext.registerReceiver(downloadReceiver, filter1);
        // 监听安装
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("android.intent.action.PACKAGE_ADDED");
        filter2.addDataScheme("package");
        mContext.registerReceiver(installedReceiver, filter2);
    }

    public void unregisterReceiver() {
        mContext.unregisterReceiver(downloadReceiver);
        mContext.unregisterReceiver(installedReceiver);
    }

    public void addAppInfo(AppInfo appInfo) {
        mAppInfoList.add(appInfo);
    }

    public void clearAppList() {
        mAppInfoList.clear();
    }

    private boolean checkBrowser(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            mContext.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private int getVersion(String packageName) {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private void downloadOrUpdate(int position) {

        // 先判断网络状态
        if (DownloadUtils.getConnectedType(mContext) == -1) {
            Toast.makeText(mContext, mContext.getString(R.string.network_tip), Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取下载模式
        boolean wifiOnly = PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("only_wifi_download", false);
        if (wifiOnly) {
            if (DownloadUtils.getConnectedType(mContext) == ConnectivityManager.TYPE_WIFI) {
                setDownloadState(position);
            } else if (DownloadUtils.getConnectedType(mContext) == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(mContext, mContext.getString(R.string.wifi_tip), Toast.LENGTH_SHORT).show();
            }
        } else {
            setDownloadState(position);
        }
    }

    private void setDownloadState(int position) {
        long lastDownloadId = DownloadUtils.download(mContext, mAppInfoList.get(position).getDownloadUrl(), mAppInfoList.get(position).getAppName(), DownloadManager.Request.VISIBILITY_VISIBLE, DownloadManager.Request.NETWORK_WIFI);
        setAppInfoState(position, INSTALLING, lastDownloadId);
        notifyDataSetChanged();
    }

    private void cancelDownloadOrUpdate(int position) {
        DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.remove(mAppInfoList.get(position).getDownloadId());
        // 判断是否已经安装
        if (checkBrowser(mAppInfoList.get(position).getPackageName())) {
            setAppInfoState(position, UPDATE, -1);
        } else {
            setAppInfoState(position, INSTALL, -1);
        }
        notifyDataSetChanged();
    }

    private void openApp(int position) {
        doStartApplicationWithPackageName(mContext, mAppInfoList.get(position).getPackageName());
    }

    private void doStartApplicationWithPackageName(Context context, String packageName) {
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.setPackage(packageName);
        resolveIntent.addCategory(CATEGORY_JIUANSTORE_LAUNCHER);
        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
        for (ResolveInfo resolveinfo : resolveInfoList) {
            String pkg = resolveinfo.activityInfo.packageName;
            String className = resolveinfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(pkg, className);
            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    private class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        private final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }

    }

}
